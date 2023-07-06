package br.com.souzabrunoj.coinquotation.presentation.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import br.com.souzabrunoj.coinquotation.R
import br.com.souzabrunoj.coinquotation.databinding.FragmentCoinDetailsBinding
import br.com.souzabrunoj.coinquotation.presentation.viewModel.CoinDetailsViewModel
import br.com.souzabrunoj.coinquotation.utils.getColor
import br.com.souzabrunoj.coinquotation.utils.getDrawable
import br.com.souzabrunoj.coinquotation.utils.handleWithFlow
import br.com.souzabrunoj.coinquotation.utils.hideWithFade
import br.com.souzabrunoj.coinquotation.utils.roundToThreeDecimals
import br.com.souzabrunoj.coinquotation.utils.roundToTwoDecimals
import br.com.souzabrunoj.coinquotation.utils.showWithFade
import br.com.souzabrunoj.coinquotation.utils.toDoubleFloatPairs
import br.com.souzabrunoj.coinquotation.utils.toLocaleDateFormat
import br.com.souzabrunoj.domain.model.CoinDetails
import coil.load
import dagger.hilt.android.AndroidEntryPoint

private const val ANIMATION_DURATION = 1000L

@AndroidEntryPoint
class CoinDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCoinDetailsBinding
    private val viewModel: CoinDetailsViewModel by viewModels()
    private val args: CoinDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCoinDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        viewModel.getCoinDetails(args.coinId)
    }

    private fun setupObservers() {
        viewModel.coinDetails.handleWithFlow(
            viewLifecycleOwner,
            onLoading = {
                binding.pbLoadingCoinDetailsFragment.showWithFade()
                handleContainersViews(false)
            },
            onSuccess = {
                handleCoinData(it)
                handlePercentage(it)
                handleLogo(it)
                handleChart(it)
                handleLink(it)
                handleDescription(it)
                handleContainersViews(true)
            },
            onFailure = { Toast.makeText(requireContext(), "There is something wrong!", Toast.LENGTH_LONG).show() },
            onComplete = { binding.pbLoadingCoinDetailsFragment.hideWithFade() }
        )
    }

    private fun handleCoinData(coin: CoinDetails) {
        binding.apply {
            tvCoinNameSymbolCoinDetailsFragment.text = getString(R.string.symbol_string_template, coin.name, coin.symbol.uppercase())
            tvCurrentPriceCoinDetailsFragment.text = coin.marketData.currentPrice.eur.roundToThreeDecimals()
            tvCategoriesCoinDetailsFragment.text = coin.categories[0]
            tvGenesisDateCoinDetailsFragment.text = coin.genesisDate.toLocaleDateFormat()
        }
    }

    private fun handleDescription(coin: CoinDetails) {
        binding.tvDescriptionCoinDetailsFragment.apply {
            text = HtmlCompat.fromHtml(coin.description.en, FROM_HTML_MODE_LEGACY)
            movementMethod = ScrollingMovementMethod()
        }
    }

    private fun handleLink(coin: CoinDetails) {
        binding.tvLinkCoinDetailsFragment.apply {
            text = getString(R.string.hyperlink_string_template,coin.links.homepage[0])
            setOnClickListener {
                val uri = Uri.parse(coin.links.homepage[0])
                val intent = Intent(Intent.ACTION_VIEW, uri)
                requireContext().startActivity(intent)
            }
        }
    }

    private fun handleChart(coin: CoinDetails) {
        binding.lcLineChartCoinDetailsFragment.apply {
            gradientFillColors = intArrayOf(getColor(R.color.chartBG), Color.TRANSPARENT)
            animation.duration = ANIMATION_DURATION
            animate(coin.marketData.sparkline7d.price.toDoubleFloatPairs())
        }
    }

    private fun handleLogo(coin: CoinDetails) {
        binding.apply {
            ivCoinLogoCoinDetailsFragment.load(coin.image.large) {
                crossfade(true)
                crossfade(500)
                placeholder(R.drawable.ic_bitcoin)
                error(R.drawable.ic_bitcoin)
            }
        }
    }

    private fun handlePercentage(data: CoinDetails) {
        binding.tvChangePercentageCoinDetailsFragment.apply {
            val number = data.marketData.priceChangePercentage24h.roundToTwoDecimals().toDouble()
            val colorAndDrawablePair: Pair<Int, Int> = when {
                number > 0 -> Color.GREEN to R.drawable.ic_arrow_drop_up
                number < 0 -> Color.RED to R.drawable.ic_arrow_drop_down
                else -> Color.LTGRAY to R.drawable.ic_minimize
            }

            text = getString(R.string.number_string_template, number.toString())
            setTextColor(colorAndDrawablePair.first)
            binding.ivArrowCoinDetailsFragment.setImageDrawable(getDrawable(colorAndDrawablePair.second))
        }
    }

    private fun handleContainersViews(isVisible: Boolean) {
        binding.apply {
            clNameCoinDetailsFragment.isVisible = isVisible
            clChartCoinDetailsFragment.isVisible = isVisible
            clDetailsCoinDetailsFragment.isVisible = isVisible
        }
    }
}
