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
import android.widget.Toast
import com.skydoves.baserecyclerviewadapter.RecyclerViewPaginator
import com.example.themoviedemo.R
import com.example.themoviedemo.extension.observeLiveData
import com.example.themoviedemo.models.Resource
import com.example.themoviedemo.models.Status
import com.example.themoviedemo.models.entity.Person
import com.example.themoviedemo.view.adapter.PeopleAdapter
import com.example.themoviedemo.view.ui.details.person.PersonDetailActivity
import com.example.themoviedemo.view.viewholder.PeopleViewHolder
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.main_fragment_movie.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import javax.inject.Inject



class PersonListFragment: Fragment(), PeopleViewHolder.Delegate {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainActivityViewModel

    private val adapter = PeopleAdapter(this)
    private lateinit var paginator: RecyclerViewPaginator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment_star, container, false)
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
                isLoading = { viewModel.getPeopleValues()?.status == Status.LOADING },
                loadMore = { loadMore(it) },
                onLast =  { viewModel.getPeopleValues()?.onLastPage!! })
    }

    private fun observeViewModel() {
        observeLiveData(viewModel.getPeopleObservable()) { updatePeople(it) }
        viewModel.postPeoplePage(1)
    }

    private fun updatePeople(resource: Resource<List<Person>>) {
        when(resource.status) {
            Status.SUCCESS -> adapter.addPeople(resource)
            Status.ERROR -> toast(resource.errorEnvelope?.status_message.toString())
            Status.LOADING -> { }
        }
    }

    private fun loadMore(page: Int) {
        viewModel.postPeoplePage(page)
    }

    override fun onItemClick(person: Person, view: View) {
        activity?.let {

            Toast.makeText(context,"clickedd",Toast.LENGTH_SHORT).show()

            PersonDetailActivity.startActivity(this, it, person, view)
        } ?: startActivity<PersonDetailActivity>("person" to person)
    }
}
