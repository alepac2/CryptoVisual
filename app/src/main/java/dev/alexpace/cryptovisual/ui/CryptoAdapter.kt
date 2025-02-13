package dev.alexpace.cryptovisual.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.alexpace.cryptovisual.R
import dev.alexpace.cryptovisual.databinding.CardCryptoBinding
import dev.alexpace.cryptovisual.domain.models.Crypto

class CryptoAdapter : RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    private val cryptos = mutableListOf<Crypto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardCryptoBinding.inflate(layoutInflater, parent, false)
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val crypto = cryptos[position]
        holder.bind(crypto)
    }

    override fun getItemCount(): Int = cryptos.size

    fun addCryptos(cryptoList: List<Crypto>) {
        cryptos.addAll(cryptoList)
        notifyItemRangeInserted(0, cryptoList.size)
    }

    class CryptoViewHolder(private val binding: CardCryptoBinding) : RecyclerView.ViewHolder(binding.root) {

        private val cryptoImage: ImageView by lazy {
            binding.cryptoImage
        }

        fun bind(crypto: Crypto) {
            binding.cryptoSymbol.text = crypto.symbol.uppercase()
            binding.cryptoPrice.text = "$${crypto.currentPrice}"

            Glide.with(itemView.context)
                .load(crypto.image)
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(cryptoImage)
        }
    }
}
