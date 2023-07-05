package br.com.souzabrunoj.coinquotation.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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
        setupListeners()
        viewModel.getCoinQuotation()
    }

    private fun setupListeners() {
        binding.btRetryHomeFragment.setOnClickListener {
            viewModel.getCoinQuotation()
            binding.btRetryHomeFragment.isVisible = false
        }

        binding.srSwipeRefreshHomeFragment.setOnRefreshListener {
            binding.btRetryHomeFragment.isVisible = false
            viewModel.getCoinQuotation(false)
        }
    }

    private fun setupObservers() {
        viewModel.coins.handleWithFlow(
            lifecycleOwner = viewLifecycleOwner,
            onLoading = { binding.pbLoadingHomeFragment.isVisible = it },
            onSuccess = ::handelCoinQuotation,
            onFailure = ::handleError,
            onComplete = ::handleOnCompleteRequest
        )
    }

    private fun handelCoinQuotation(list: List<Coin>) {
        adapter.differ.submitList(list)
        binding.pbLoadingHomeFragment.isVisible = false
        binding.rvCoinListHomeFragment.isVisible = true
    }

    private fun setupRecyclerView() {
        binding.rvCoinListHomeFragment.apply {
            adapter = this@HomeFragment.adapter
        }
    }

    private fun handleError(error: Throwable) {
        Snackbar.make(binding.root, error.message.orEmpty(), Snackbar.LENGTH_SHORT).show()
        binding.apply {
            btRetryHomeFragment.isVisible = true
            rvCoinListHomeFragment.isVisible = false
        }
    }

    private fun handleOnCompleteRequest() {
        binding.apply {
            srSwipeRefreshHomeFragment.isRefreshing = false
            pbLoadingHomeFragment.isVisible = false
        }
    }

    private fun onCoinItemClick(coin: Coin) {

    }
}