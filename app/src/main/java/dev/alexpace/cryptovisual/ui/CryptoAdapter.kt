package dev.alexpace.cryptovisual.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.alexpace.cryptovisual.domain.models.Crypto

class CryptoAdapter (private val cryptos: List<Crypto>): RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        val crypto = cryptos[position]
        holder.binding.image.setImageResource(crypto.image)
        holder.binding.name.text = crypto.name
        holder.binding.symbol.text = crypto.symbol
        holder.binding.price.text = crypto.currentPrice.toString()
    }

    override fun getItemCount(): Int {
        return cryptos.size
    }

    inner class CryptoViewHolder(val binding: ActivityMainBinding) : RecyclerView.ViewHolder(binding.root)
}