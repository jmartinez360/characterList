package com.android.lidlapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.lidlapp.R
import com.android.lidlapp.databinding.ActivityMainBinding
import com.android.lidlapp.model.TicketModel
import com.android.lidlapp.ui.recycler.adapter.TicketsAdapter
import com.android.lidlapp.ui.recycler.decoration.MarginItemDecoration
import com.android.lidlapp.ui.recycler.listener.TicketItemListener
import com.android.lidlapp.ui.utils.AppBarOffsetListener
import com.android.lidlapp.ui.utils.AppbarChangedListener
import com.android.lidlapp.viewModel.TicketListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), AppbarChangedListener, TicketItemListener {

    private val ticketsViewModel: TicketListViewModel by viewModel()

    private val adapter by lazy { TicketsAdapter(this) }
    private val layoutManager by lazy { LinearLayoutManager(this, RecyclerView.VERTICAL, false) }
    private val itemDecoration by lazy { MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.ticket_list_margin)) }
    private val appBarOffsetListener by lazy { AppBarOffsetListener(this) }

    private var activatedTicketsText = ""
    private var activatedTicketsCount = 0

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupObservers()
        getTicketList()
    }

    private fun setupViews() {
        setupAppBar()
        setupRecycler()
    }

    private fun setupAppBar() {
        binding.appBar.addOnOffsetChangedListener(appBarOffsetListener)
    }

    override fun onAppBarCollapsed() {
        showCollapsedAppBarTitle()
        hideAppBarSubtitle()
    }

    override fun onAppBarExpanded() {
        showAppbarExpandedTitle()
        showAppBarSubtitle()
    }

    override fun onAppBarIdled() {
        showAppbarExpandedTitle()
        showAppBarSubtitle()
    }

    private fun showAppbarExpandedTitle() {
        binding.collapsingToolbar.title = getString(R.string.tickets_title)
    }

    private fun showCollapsedAppBarTitle() {
        binding.collapsingToolbar.title = activatedTicketsText
    }

    private fun showAppBarSubtitle() {
        binding.collapsingToolbar.subtitle = activatedTicketsText
        setSubtitleColorDependingActivatedTicketsCount()
    }

    private fun setSubtitleColorDependingActivatedTicketsCount() {
        if (activatedTicketsCount > 0) {
            binding.collapsingToolbar.setExpandedSubtitleTextColor(getColor(R.color.mainTitle))
        } else {
            binding.collapsingToolbar.setExpandedSubtitleTextColor(getColor(R.color.noActivatedTickets))
        }
    }

    private fun hideAppBarSubtitle() {
        binding.collapsingToolbar.subtitle = ""
    }

    private fun setupRecycler() {
        binding.recycler.apply {
            adapter = this@MainActivity.adapter
            layoutManager = this@MainActivity.layoutManager
            addItemDecoration(itemDecoration)
        }
    }

    override fun onActivationButtonPressed(ticket: TicketModel) {
        ticketsViewModel.activateDeactivateTicket(ticket)
    }

    override fun onTicketClick(ticket: TicketModel) {
        startActivity(TicketDetailActivity.getIntent(this, ticket.id))
    }

    private fun setupObservers() {
        setupTicketsObserver()
        setupActivatedTicketsCountObserver()
        setupActivationTicketObservers()
    }

    private fun setupTicketsObserver() {
        ticketsViewModel.ticketListLiveData.observe(this, Observer { showTickets(it) })
    }

    private fun showTickets(tickets: List<TicketModel>) {
        adapter.setTickets(tickets)
    }

    private fun setupActivatedTicketsCountObserver() {
        ticketsViewModel.activatedTicketsCountLiveData.observe(this,
            Observer { setupTicketsActivatedCountTextResource(it) })
    }

    private fun setupTicketsActivatedCountTextResource(activatedTicketsCount: Int) {
        this.activatedTicketsCount = activatedTicketsCount
        activatedTicketsText = resources.getQuantityString(R.plurals.activated_tickets,
            activatedTicketsCount, activatedTicketsCount)
        showActivatedTicketsCount()
    }

    private fun showActivatedTicketsCount() {
        if (appBarOffsetListener.currentState == AppBarOffsetListener.EXPANDED) {
            onAppBarExpanded()
        } else  {
            onAppBarCollapsed()
        }
    }

    private fun setupActivationTicketObservers() {
        setupTicketActivatedObserver()
        setupTicketDeactivatedObserver()
    }

    private fun setupTicketActivatedObserver() {
        ticketsViewModel.activatedTicketLiveData.observe(this, Observer {
            adapter.activateTicket(it)
        })
    }

    private fun setupTicketDeactivatedObserver() {
        ticketsViewModel.deactivatedTicketLiveData.observe(this, Observer {
            adapter.deactivateTicket(it)
        })
    }

    private fun getTicketList() {
        ticketsViewModel.getTicketList()
    }
}