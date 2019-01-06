package com.example.themoviedemo.view.ui.main

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.example.themoviedemo.R
import com.example.themoviedemo.extension.observeLiveData
import com.example.themoviedemo.models.Resource
import com.example.themoviedemo.models.Status
import com.example.themoviedemo.models.entity.Movie
import com.example.themoviedemo.view.adapter.MovieListAdapter
import com.example.themoviedemo.view.ui.details.movie.MovieDetailActivity
import com.example.themoviedemo.view.viewholder.MovieListViewHolder
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.main_fragment_movie.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject



class MovieListFragment : Fragment(), MovieListViewHolder.Delegate {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainActivityViewModel

    private val adapter = MovieListAdapter(this)
    private lateinit var paginator: RecyclerViewPaginator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeUI()
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)
        observeViewModel()
    }

    private fun initializeUI() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        paginator = RecyclerViewPaginator(
                recyclerView = recyclerView,
                isLoading = { viewModel.getMovieListValues()?.status == Status.LOADING },
                loadMore = { loadMore(it) },
                onLast =  { viewModel.getMovieListValues()?.onLastPage!! }
        )
        paginator.currentPage = 1
    }

    private fun observeViewModel() {
        observeLiveData(viewModel.getMovieListObservable()) { updateMovieList(it) }
        viewModel.postMoviePage(1)
    }

    private fun updateMovieList(resource: Resource<List<Movie>>) {
        when(resource.status) {
            Status.SUCCESS -> adapter.addMovieList(resource)
            Status.ERROR -> toast(resource.errorEnvelope?.status_message.toString())
            Status.LOADING -> { }
        }
    }

    private fun loadMore(page: Int) {
        viewModel.postMoviePage(page)
    }

    override fun onItemClick(movie: Movie) {
        startActivity<MovieDetailActivity>("movie" to movie)
    }
}
