package space.irsi7.cryptoviewer.coin

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import space.irsi7.cryptoviewer.R
import space.irsi7.cryptoviewer.model.Value
import space.irsi7.cryptoviewer.ui.main.MainViewModel

//Класс хранящий картинки из одной директории
class CoinsList : Fragment() {
    private lateinit var fileList: RecyclerView
    private lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var adapter: CoinAdapter
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
            .make(container!!, "Произошла ошибка при загрузке", Snackbar.LENGTH_SHORT)
            .setBackgroundTint(resources.getColor(R.color.redDown))
        // Setup refresh listener which triggers new data loading

        swipeContainer.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            viewModel.getNewCoinList()
            viewModel.isDownloadingRe.observe(viewLifecycleOwner){
                if(it != null && !it){
                    if(viewModel.isFailRe){
                        error.show()
                    } else {
                        success.show()
                        adapter.updateAdapter()
                    }
                    swipeContainer.isRefreshing = false
                }
            }

        }

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_light)

        viewModel.chosenVal.observe(viewLifecycleOwner) {
            if(it != null) {
                when (it) {
                    0 -> updateAdapter(viewModel.valueUSD.value!!)
                    1 -> updateAdapter(viewModel.valueEUR.value!!)
                }
            }
        }
        setAdapter()
        return swipeContainer
    }

    private fun setAdapter() {
        adapter = CoinAdapter(context, viewModel.tokenData.value, viewModel.valueUSD.value, viewModel)
        fileList.adapter = adapter
    }

    private fun updateAdapter(values: List<Value>) {
        adapter.updateValuesSet(values)
    }


    companion object {
        fun newInstance() = CoinsList()
    }
}