package br.com.souzabrunoj.coinquotation.base.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.souzabrunoj.coinquotation.R
import br.com.souzabrunoj.coinquotation.databinding.ItemCoinBinding
import br.com.souzabrunoj.coinquotation.utils.roundToThreeDecimals
import br.com.souzabrunoj.coinquotation.utils.toDoubleFloatPairs
import br.com.souzabrunoj.domain.model.Coin
import coil.load

private const val ANIMATION_DURATION = 1000L
typealias onItemClick = (Coin) -> Unit

class CoinAdapter(private val onClick: onItemClick) : RecyclerView.Adapter<CoinAdapter.ViewHolder>() {

    private lateinit var binding: ItemCoinBinding

    private val differCallback = object : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemCoinBinding.inflate(inflater, parent, false)
        return ViewHolder()
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Coin) {
            binding.apply {
                tvNameItemCoin.text = item.id
                tvSymbolItemCoin.text = item.symbol.uppercase()
                tvPriceItemCoin.text = "€${item.currentPrice.roundToThreeDecimals()}"
                ivCryptoItemCoin.load(item.image) {
                    crossfade(true)
                    crossfade(500)
                    placeholder(R.drawable.ic_bitcoin)
                    error(R.drawable.ic_bitcoin)
                }

                lcLineChartItemCoin.gradientFillColors = intArrayOf(Color.parseColor("#2a9085"), Color.TRANSPARENT)
                lcLineChartItemCoin.animation.duration = ANIMATION_DURATION
                lcLineChartItemCoin.animate(item.sparkline.price.toDoubleFloatPairs())
                root.setOnClickListener { onClick(item) }
            }
        }
    }
}