package dev.alexpace.cryptovisual.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.alexpace.cryptovisual.R
import dev.alexpace.cryptovisual.databinding.CardCryptoBinding
import dev.alexpace.cryptovisual.domain.models.Crypto

class CryptoAdapter(private val itemViewClickListener: (Crypto) -> Unit) :
    RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    // Variables and values
    private val cryptos = mutableListOf<Crypto>()
    private var filteredCryptos = mutableListOf<Crypto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardCryptoBinding.inflate(layoutInflater, parent, false)
        return CryptoViewHolder(binding, itemViewClickListener)
    }

    /**
     * Binds the data to the view holder
     */
    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val crypto = filteredCryptos[position]
        holder.bind(crypto)
    }

    /**
     * Not necessary but mandatory for RecyclerView
     */
    override fun getItemCount(): Int = filteredCryptos.size

    /**
     * Clears totally the list and adds the new list, which can be filtered
     */
    @SuppressLint("NotifyDataSetChanged")
    fun addCryptos(cryptoList: List<Crypto>) {
        cryptos.clear()
        cryptos.addAll(cryptoList)
        filteredCryptos = ArrayList(cryptos)
        notifyDataSetChanged()
    }

    /**
     * Clears all the cryptos from the list
     */
    @SuppressLint("NotifyDataSetChanged")
    fun clearCryptos() {
        cryptos.clear()
        filteredCryptos.clear()
        notifyDataSetChanged()
    }

    /**
     * Filtering logic. Works if the Crypto's name or symbol contains the input
     */
    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredCryptos = if (query.isEmpty()) {
            ArrayList(cryptos)
        } else {
            cryptos.filter {
                it.name.contains(query, ignoreCase = true) || it.symbol.contains(
                    query,
                    ignoreCase = true
                )
            }.toMutableList()
        }
        notifyDataSetChanged()
    }

    /**
     * ViewHolder class for the RecyclerView, binds every value to the correspondent View
     */
    class CryptoViewHolder(
        private val binding: CardCryptoBinding,
        private val itemViewClickListener: (Crypto) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(crypto: Crypto) {
            binding.cryptoSymbol.text = crypto.symbol.uppercase()
            binding.cryptoName.text = crypto.name
            binding.cryptoPrice.text = "$${crypto.currentPrice}"
            binding.cryptoTotalVolume.text = "Total Volume: $${crypto.totalVolume}"

            Glide.with(itemView.context)
                .load(crypto.image)
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(binding.cryptoImage)

            itemView.setOnClickListener {
                itemViewClickListener(crypto)
            }
        }
    }
}