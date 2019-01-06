package com.example.themoviedemo.view.ui.details.person

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.core.view.ViewCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.themoviedemo.R
import com.example.themoviedemo.api.Api
import com.example.themoviedemo.extension.checkIsMaterialVersion
import com.example.themoviedemo.extension.observeLiveData
import com.example.themoviedemo.extension.visible
import com.example.themoviedemo.models.Resource
import com.example.themoviedemo.models.Status
import com.example.themoviedemo.models.entity.Person
import com.example.themoviedemo.models.network.PersonDetail
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_person_detail.*
import kotlinx.android.synthetic.main.toolbar_default.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import javax.inject.Inject


class PersonDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(PersonDetailViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)
        supportPostponeEnterTransition()

        initializeUI()
    }

    private fun initializeUI() {
        toolbar_home.setOnClickListener { onBackPressed() }
        toolbar_title.text = getPersonFromIntent().name
        getPersonFromIntent().profile_path?.let {
            Glide.with(this).load(Api.getPosterPath(it))
                    .apply(RequestOptions().circleCrop())
                    .listener(object: RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            supportStartPostponedEnterTransition()
                            observeViewModel()
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            supportStartPostponedEnterTransition()
                            observeViewModel()
                            return false
                        }
                    })
                    .into(person_detail_profile)
        }
        person_detail_name.text = getPersonFromIntent().name
    }

    private fun observeViewModel() {
        observeLiveData(viewModel.getPersonObservable()) { updatePersonDetail(it)}
        viewModel.postPersonId(getPersonFromIntent().id)
    }

    private fun updatePersonDetail(resource: Resource<PersonDetail>) {
        when(resource.status) {
            Status.SUCCESS -> {
                resource.data?.let {
                    person_detail_biography.text = it.biography
                    detail_person_tags.tags = it.also_known_as

                    if(it.also_known_as.isNotEmpty()) {
                        detail_person_tags.visible()
                    }
                }
            }
            Status.ERROR -> toast(resource.errorEnvelope?.status_message.toString())
            Status.LOADING -> { }
        }
    }

    private fun getPersonFromIntent(): Person {
        return intent.getParcelableExtra("person") as Person
    }

    companion object {
        const val intent_requestCode = 1000

        fun startActivity(fragment: Fragment, activity: FragmentActivity, person: Person, view: View) {
            if (activity.checkIsMaterialVersion()) {
                val intent = Intent(activity, PersonDetailActivity::class.java)
                ViewCompat.getTransitionName(view)?.let {
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, it)
                    intent.putExtra("person", person)
                    activity.startActivityFromFragment(fragment, intent, intent_requestCode, options.toBundle())
                }
            } else {
                activity.startActivityForResult<PersonDetailActivity>(intent_requestCode, "person" to person)
            }
        }
    }
}
