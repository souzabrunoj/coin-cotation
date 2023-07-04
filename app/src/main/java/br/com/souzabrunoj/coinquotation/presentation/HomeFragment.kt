package br.com.souzabrunoj.coinquotation.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import br.com.souzabrunoj.coinquotation.adapter.CoinAdapter
import br.com.souzabrunoj.coinquotation.databinding.FragmentHomeBinding
import br.com.souzabrunoj.coinquotation.presentation.viewModel.CoinQuotationViewModel
import br.com.souzabrunoj.coinquotation.utils.handleWithFlow
import br.com.souzabrunoj.domain.model.Coin
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: CoinQuotationViewModel by viewModels()
    private val adapter: CoinAdapter by lazy { CoinAdapter(::onCoinItemClick) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        viewModel.init()
    }

    private fun setupObservers() {
        viewModel.coins.handleWithFlow(
            lifecycleOwner = viewLifecycleOwner,
            onLoading = { binding.pbLoadingHomeFragment.isVisible = true },
            onSuccess = ::handelCoinQuotation,
            onFailure = { Snackbar.make(binding.root, it.message.orEmpty(), Snackbar.LENGTH_SHORT).show() },
            onComplete = { binding.pbLoadingHomeFragment.isVisible = false }
        )
    }

    private fun handelCoinQuotation(list: List<Coin>) {
        adapter.differ.submitList(list)
        binding.pbLoadingHomeFragment.isVisible = false
    }

    private fun setupRecyclerView() {
        binding.rvCoinListHomeFragment.apply {
            adapter = this@HomeFragment.adapter
        }
    }

    private fun onCoinItemClick(coin: Coin) {

    }
}