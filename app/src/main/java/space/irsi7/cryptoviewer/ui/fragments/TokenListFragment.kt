package space.irsi7.cryptoviewer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import space.irsi7.cryptoviewer.R
import space.irsi7.cryptoviewer.model.Currency
import space.irsi7.cryptoviewer.ui.viewModels.MainViewModel

//Класс хранящий картинки из одной директории
class TokenListFragment : Fragment() {
    private lateinit var fileList: RecyclerView
    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var adapter: TokenListAdapter
    private lateinit var glm: GridLayoutManager
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        swipeContainer = inflater.inflate(R.layout.tokens_list, container, false) as SwipeRefreshLayout
        fileList = swipeContainer.findViewById(R.id.tokensList)
        glm = GridLayoutManager(context, 1)
        fileList.layoutManager = glm
        val success =   Snackbar
            .make(container!!, "Обновление прошло успешно", Snackbar.LENGTH_SHORT)
            .setBackgroundTint(resources.getColor(R.color.greenUP))
        val error =   Snackbar
            .make(container, "Произошла ошибка при загрузке", Snackbar.LENGTH_SHORT)
            .setBackgroundTint(resources.getColor(R.color.redDown))
        // Setup refresh listener which triggers new data loading

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_light)

        swipeContainer.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            GlobalScope.launch {
                val result = viewModel.reloadTokenListAsync().await()
                if(result){
                    error.show()
                } else {
                    success.show()
                }
                swipeContainer.isRefreshing = false
            }
        }


        viewModel.currentCurrency.observe(viewLifecycleOwner) {
            if(it != null) {
                updateAdapter(it)
            }
        }
        setAdapter()
        return swipeContainer
    }

    private fun setAdapter() {
        adapter = TokenListAdapter(context, viewModel.tokensList.value, viewModel.tokensValues[Currency.USD], viewModel)
        fileList.adapter = adapter
    }

    private fun updateAdapter(currency: Currency) {
        adapter.updateValuesSet(currency)
    }

    companion object {
        fun newInstance() = TokenListFragment()
    }
}