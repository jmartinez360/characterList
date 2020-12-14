package com.android.lidlapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.lidlapp.R
import com.android.lidlapp.databinding.ActivityTicketDetailBinding
import com.android.lidlapp.model.TicketDetailModel
import com.android.lidlapp.ui.recycler.adapter.RelatedProductsAdapter
import com.android.lidlapp.ui.recycler.decoration.HorizontalMarginItemDecoration
import com.android.lidlapp.ui.utils.CustomScrollViewListener
import com.android.lidlapp.ui.utils.ScrollViewCallback
import com.android.lidlapp.ui.utils.setTicketDiscountText
import com.android.lidlapp.utils.ImageLoader
import com.android.lidlapp.utils.TicketDateUtils
import com.android.lidlapp.viewModel.TicketDetailViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TicketDetailActivity : AppCompatActivity(), ScrollViewCallback {

    private val ticketDetailViewModel: TicketDetailViewModel by viewModel()
    private val dateUtils: TicketDateUtils by inject()
    private val imageLoader: ImageLoader by inject()

    private val adapter by lazy { RelatedProductsAdapter() }
    private val layoutManager by lazy { LinearLayoutManager(this, RecyclerView.HORIZONTAL, false) }
    private val itemDecoration by lazy {
        HorizontalMarginItemDecoration(resources.getDimensionPixelSize(R.dimen.ticket_list_margin))
    }

    private lateinit var binding: ActivityTicketDetailBinding
    private lateinit var ticket: TicketDetailModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTicketDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupObservers()
        getTicketDetail()
    }

    private fun setupViews() {
        setupBackButton()
        setupProductsRecycler()
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener { onBackPressed() }
    }

    override fun onFirstViewPartiallyVisible() {
        setupCompleteToolbar()
    }

    private fun setupCompleteToolbar() {
        setToolbarElevationAndBackground(resources.getDimension(R.dimen.app_bar_elevation), resources.getColor(R.color.white))
        setupToolbarDiscountText()
        setupBackButtonColorRes(R.drawable.ic_baseline_arrow_back)
    }

    private fun setupToolbarDiscountText() {
        binding.toolbarDiscount.text = getString(R.string.toolbar_discount, ticket.discount.description)
        binding.toolbarDiscount.visibility = View.VISIBLE
    }

    private fun setToolbarElevationAndBackground(elevation: Float, backgroundColor: Int) {
        binding.appBar.setBackgroundColor(backgroundColor)
        binding.appBar.elevation = elevation
    }

    private fun setupBackButtonColorRes(drawableRes: Int) {
        binding.backButton.setImageDrawable(resources.getDrawable(drawableRes))
    }

    override fun onFirstViewCompletelyVisible() {
        setupSemiVisibleToolbar()
    }

    private fun setupSemiVisibleToolbar() {
        setToolbarElevationAndBackground(elevation = 0f, backgroundColor = resources.getColor(android.R.color.transparent))
        setupBackButtonColorRes(R.drawable.ic_baseline_arrow_back_white)
        binding.toolbarDiscount.visibility = View.GONE
    }

    private fun setupProductsRecycler() {
        binding.relatedProducts.relatedProductsRecycler.adapter = adapter
        binding.relatedProducts.relatedProductsRecycler.layoutManager = layoutManager
        binding.relatedProducts.relatedProductsRecycler.addItemDecoration(itemDecoration)
    }

    private fun setupObservers() {
        setupTicketDetailObserver()
        setupLoadingObserver()
    }

    private fun setupLoadingObserver() {
        ticketDetailViewModel.loadingLiveData.observe(this, Observer {
            setupProgressbar(it)
        })
    }

    private fun setupProgressbar(shouldShow: Boolean) {
        if (shouldShow) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setupTicketDetailObserver() {
        ticketDetailViewModel.ticketDetailLiveData.observe(this, Observer {
            setupTicketDetail(it)
        })
    }

    private fun setupTicketDetail(ticket: TicketDetailModel) {
        this.ticket = ticket
        setupImage()
        setupDiscount()
        setupActivation()
        setupProductDescription()
        setupRemainingTime()
        setupProductLimit()
        setupRelatedProducts()
        setupProductCode()
    }

    private fun setupImage() {
        imageLoader.loadImageIntoView(ticket.image!!, binding.ticketImage)
    }

    private fun setupDiscount() {
        binding.discount.discountText.setTicketDiscountText(
            ticket.discount.description,
            ticket.discount.type
        )
        binding.discount.discountLabel.setTicketDiscountText(discountType = ticket.discount.type)
        binding.discount.discountLabel.visibility = View.VISIBLE
        binding.discount.discountContainer.setTicketColorDependingDiscount(ticket.discount.type)
        binding.scrollview.setOnScrollChangeListener(
            CustomScrollViewListener(
                this,
                binding.discount.discountContainer.y
            )
        )
    }

    private fun setupActivation() {
        val activationText = getString(getActivationTextRes())
        if (ticket.activated!!) binding.activation.activateButton.setActivated()
        else binding.activation.activateButton.setDeactivated()
        binding.activation.activateText.text = activationText
        binding.activation.activateButton.visibility = View.VISIBLE
    }

    private fun getActivationTextRes(): Int {
        return if (ticket.activated!!) R.string.activated else R.string.deactivated
    }

    private fun setupProductDescription() {
        binding.description.companyText.text = ticket.company
        binding.description.ticketNameText.text = ticket.name
        binding.description.descriptionText.text = ticket.description
    }

    private fun setupRemainingTime() {
        val remainingDays = dateUtils.getRemainingDays(ticket.endDate!!)
        binding.remainingTime.remainingTimeText.text =
            resources.getQuantityString(R.plurals.remaining_end_time, remainingDays, remainingDays)
        binding.remainingTime.calendarIcon.visibility = View.VISIBLE
    }

    private fun setupProductLimit() {
        binding.exchange.limitText.text =
            resources.getQuantityString(R.plurals.limit, ticket.limit, ticket.limit)
        binding.exchange.exchangeLimitText.text = resources.getQuantityString(
            R.plurals.exchange,
            ticket.exchangeLimit,
            ticket.exchangeLimit
        )
    }

    private fun setupRelatedProducts() {
        if (shouldShowRelatedProducts()) {
            binding.relatedProducts.relatedProductsContainer.visibility = View.VISIBLE
            adapter.addProducts(ticket.relatedProducts)
        } else {
            binding.relatedProducts.relatedProductsContainer.visibility = View.GONE
        }
    }

    private fun shouldShowRelatedProducts() = ticket.relatedProducts.isNotEmpty()

    private fun setupProductCode() {
        binding.productCode.productCodeText.text = ticket.productCode.toString()
    }

    private fun getTicketDetail() {
        val ticketId = intent.getStringExtra(TICKET_ID) ?: ""
        ticketDetailViewModel.getTicketDetail(ticketId)
    }

    companion object {

        private const val TICKET_ID = "TICKET_ID"

        fun getIntent(context: Context, ticketId: String): Intent {
            val intent = Intent(context, TicketDetailActivity::class.java)
            intent.putExtra(TICKET_ID, ticketId)
            return intent
        }
    }
}