package br.com.souzabrunoj.coinquotation.presentation.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
    private lateinit var context: Context
    private var coinSymbol: String = ""

    private val differCallback = object : DiffUtil.ItemCallback<Coin>() {
        override fun areItemsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Coin, newItem: Coin): Boolean {
            return oldItem.name == newItem.name
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemCoinBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    fun updateData(list: List<Coin>, coinSymbol: String) {
        differ.submitList(list)
        this.coinSymbol = coinSymbol
        notifyDataSetChanged()
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Coin) {
            binding.apply {
                tvNameItemCoin.text = item.id
                tvSymbolItemCoin.text = item.symbol.uppercase()
                tvPriceItemCoin.text = context.getString(R.string.two_string_template, coinSymbol, item.currentPrice.roundToThreeDecimals())
                ivCryptoItemCoin.load(item.image) {
                    crossfade(true)
                    crossfade(500)
                    placeholder(R.drawable.ic_bitcoin)
                    error(R.drawable.ic_bitcoin)
                }

                lcLineChartItemCoin.gradientFillColors = intArrayOf(ContextCompat.getColor(context, R.color.chartBG), Color.TRANSPARENT)
                lcLineChartItemCoin.animation.duration = ANIMATION_DURATION
                lcLineChartItemCoin.animate(item.sparkline.price.toDoubleFloatPairs())
                root.setOnClickListener { onClick(item) }
            }
        }
    }
}