package dev.alexpace.cryptovisual.ui.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.datepicker.MaterialDatePicker
import dev.alexpace.cryptovisual.databinding.FragmentCryptoHistoryChartBinding
import dev.alexpace.cryptovisual.domain.models.CryptoHistory
import dev.alexpace.cryptovisual.ui.viewModels.CryptoHistoryChartViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CryptoHistoryChartFragment : Fragment() {

    // Arguments passed from the CryptoListFragment (navigation graph)
    private val args: CryptoHistoryChartFragmentArgs by navArgs()

    // Variables and values
    private var _binding: FragmentCryptoHistoryChartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CryptoHistoryChartViewModel by viewModels {
        CryptoHistoryChartViewModel.Factory
    }
    private lateinit var lineChart: LineChart

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCryptoHistoryChartBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    /**
     * Initialize most of the logic when the view has already been created
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initChart()
        initListeners()
        fetchCryptoHistory(args.cryptoId)
    }

    private fun initListeners() {
        binding.showDateRangePickerButton.setOnClickListener {
            onClickRangePicker()
        }
    }

    private fun onClickRangePicker() {

        val builder = MaterialDatePicker.Builder.dateRangePicker()
        builder.setTitleText("Select Date Range")
        val picker = builder.build()

        picker.addOnPositiveButtonClickListener { selection ->
            val startDateMillis = selection.first ?: 0L
            val endDateMillis = selection.second ?: 0L

            // Convert the timestamps to Unix time in seconds, if needed
            val startUnix = startDateMillis / 1000
            val endUnix = endDateMillis / 1000

            fetchCryptoHistoryInDateRange(args.cryptoId, startUnix.toString(), endUnix.toString())
        }

        picker.show(childFragmentManager, picker.toString())
    }

    /**
     * Initialize the chart with custom settings according to MPAndroidChart documentation
     * and with the help of AI
     */
    private fun initChart() {
        lineChart = binding.historyChart

        lineChart.apply {
            description.isEnabled = false
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 86400f * 30f // One month (Unix time in seconds)
                valueFormatter = object : ValueFormatter() {
                    @SuppressLint("ConstantLocale")
                    private val dateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
                    override fun getFormattedValue(value: Float): String {
                        return dateFormat.format(Date(value.toLong() * 1000))
                    }
                }
            }
            axisRight.isEnabled = false
            axisLeft.apply {
                setDrawGridLines(true)
                textColor = Color.BLACK
            }
            xAxis.setDrawGridLines(false)
        }
    }

    /**
     * Populate the chart with the fetched data
     */
    private fun populateChart(history: List<CryptoHistory>) {
        // Clear any existing data
        lineChart.clear()

        val entries = history.map { Entry(it.timestamp.toFloat(), it.price.toFloat()) }

        val dataSet = LineDataSet(entries, "Price History").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            setDrawCircles(false)
            setDrawValues(false)
        }

        lineChart.data = LineData(dataSet)
        lineChart.invalidate()  // Redraw the chart
    }

    /**
     * Fetch crypto history from the repository and update the LiveData
     */
    private fun fetchCryptoHistory(cryptoId: String) {
        viewModel.getCryptoHistory(cryptoId).observe(viewLifecycleOwner) { history ->
            if (history.isNotEmpty()) {
                Log.d(
                    "CryptoDetailsFragment",
                    "Crypto history fetched: ${history.size} items")
                populateChart(history)
            } else {
                Log.d("CryptoDetailsFragment","No crypto history found in repository.")
            }
        }
    }

    private fun fetchCryptoHistoryInDateRange(cryptoId: String, dateStart: String, dateEnd: String) {
        viewModel.getCryptoHistoryByDateRange(cryptoId, dateStart, dateEnd).observe(viewLifecycleOwner) { history ->

            Log.d("CryptoDetailsFragment", "Fetched history: $history")

            if (history.isNotEmpty()) {
                Log.d(
                    "CryptoDetailsFragment",
                    "Crypto history by date range fetched: ${history.size} items")
                populateChart(history)
            } else {
                Log.d("CryptoDetailsFragment", "No crypto history found in repository.")
                Toast.makeText(requireContext(), "No data available for this range", Toast.LENGTH_SHORT).show()
            }
        }
    }
}