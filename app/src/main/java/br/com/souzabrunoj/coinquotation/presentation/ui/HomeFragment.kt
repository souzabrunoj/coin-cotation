package br.com.souzabrunoj.coinquotation.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.souzabrunoj.coinquotation.R
import br.com.souzabrunoj.coinquotation.presentation.adapter.CoinAdapter
import br.com.souzabrunoj.coinquotation.databinding.FragmentHomeBinding
import br.com.souzabrunoj.coinquotation.presentation.viewModel.CoinQuotationViewModel
import br.com.souzabrunoj.coinquotation.utils.CoinName
import br.com.souzabrunoj.coinquotation.utils.handleWithFlow
import br.com.souzabrunoj.coinquotation.utils.hide
import br.com.souzabrunoj.coinquotation.utils.show
import br.com.souzabrunoj.domain.model.Coin
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: CoinQuotationViewModel by viewModels()
    private val adapter: CoinAdapter by lazy { CoinAdapter(::onCoinItemClick) }
    private val navController by lazy { findNavController() }

    private var coinSelected = CoinName.DOLLAR
    private var selectedItem = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupListeners()
        viewModel.getCoinQuotation(coinSymbol = coinSelected.abbreviation)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                filter()
                return true
            }

            else -> false
        }
    }

    private fun setupListeners() {
        binding.btRetryHomeFragment.setOnClickListener {
            viewModel.getCoinQuotation(coinSymbol = coinSelected.abbreviation)
            binding.btRetryHomeFragment.hide()
        }

        binding.srSwipeRefreshHomeFragment.setOnRefreshListener {
            binding.btRetryHomeFragment.hide()
            viewModel.getCoinQuotation(showLoading = false, coinSymbol = coinSelected.abbreviation)
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
        adapter.updateData(list, coinSelected.symbol)
        binding.rvCoinListHomeFragment.scrollToPosition(0)
        binding.pbLoadingHomeFragment.isVisible = false
        binding.rvCoinListHomeFragment.show()
    }

    private fun setupRecyclerView() {
        binding.rvCoinListHomeFragment.apply {
            adapter = this@HomeFragment.adapter
        }
    }

    private fun handleError(error: Throwable) {
        Snackbar.make(binding.root, error.message.orEmpty(), Snackbar.LENGTH_SHORT).show()
        binding.apply {
            btRetryHomeFragment.show()
            rvCoinListHomeFragment.hide()
        }
    }

    private fun handleOnCompleteRequest() {
        binding.apply {
            srSwipeRefreshHomeFragment.isRefreshing = false
            pbLoadingHomeFragment.isVisible = false
        }
    }

    private fun onCoinItemClick(coin: Coin) {
        navController.navigate(HomeFragmentDirections.fromCoinHomeToCoinDetailsFragment(coin.id))
    }

    private fun filter() {
        AlertDialog.Builder(requireContext()).apply {
            val list = arrayOf(CoinName.DOLLAR.name, CoinName.REAL.name, CoinName.EURO.name)
            setSingleChoiceItems(list, selectedItem) { dialog, item ->
                coinSelected = when (item) {
                    0 -> CoinName.DOLLAR
                    1 -> CoinName.REAL
                    else -> CoinName.EURO
                }
                selectedItem = item
                viewModel.getCoinQuotation(coinSymbol = coinSelected.abbreviation)
                dialog.dismiss()
            }
        }.create().show()
    }
}