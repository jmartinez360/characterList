package com.android.lidlapp.ui.recycler.viewholder

import android.os.Bundle
import android.view.View
import com.android.lidlapp.R
import com.android.lidlapp.databinding.TicketItemBinding
import com.android.lidlapp.model.TicketModel
import com.android.lidlapp.ui.recycler.listener.TicketItemListener
import com.android.lidlapp.ui.utils.setTicketDiscountText
import com.android.lidlapp.utils.ImageLoader
import com.android.lidlapp.utils.TicketDateUtils
import org.koin.core.KoinComponent
import org.koin.core.inject

class TicketViewHolder constructor(
    itemView: View,
    private val ticketItemListener: TicketItemListener
) : BaseViewHolder<TicketModel>(itemView), KoinComponent {

    private val dateUtils: TicketDateUtils by inject()
    private val imageLoader: ImageLoader by inject()

    private val binding = TicketItemBinding.bind(itemView)

    private lateinit var currentItem: TicketModel

    override fun bindItem(item: TicketModel) {
        currentItem = item
        setupTicketItem()
    }

    private fun setupTicketItem() {
        setupImage()
        setupTitle()
        setupSubtitle()
        setActivationButtonState()
        setupDiscount()
        setupClickListeners()
    }

    private fun setupImage() {
        imageLoader.loadImageIntoView(currentItem.image!!,
        binding.ticketImage)
    }

    private fun setupTitle() {
        binding.ticketTitle.text = currentItem.name
    }

    private fun setupSubtitle() {
        val remainingDays = dateUtils.getRemainingDays(currentItem.endDate!!)
        binding.remainingTime.text =
            itemView.context.resources.getQuantityString(R.plurals.remaining_end_time, remainingDays, remainingDays)
    }

    private fun setActivationButtonState() {
        if (currentItem.activated) {
            binding.activateButton.setActivated()
        } else {
            binding.activateButton.setDeactivated()
        }
    }

    private fun setupDiscount() {
        setupDiscountText()
        setupDiscountColor()
    }

    private fun setupDiscountText() {
        binding.discount.setTicketDiscountText(
            currentItem.discount.description,
            currentItem.discount.type
        )
        binding.discountLabel.setTicketDiscountText(discountType = currentItem.discount.type)
    }

    private fun setupDiscountColor() {
        binding.discountContainer.setTicketColorDependingDiscount(currentItem.discount.type)
    }

    private fun setupClickListeners() {
        setupActivationButtonClickListener()
        setupTicketClickListener()
    }

    private fun setupActivationButtonClickListener() {
        binding.activateButton.setOnClickListener {
            binding.activateButton.setLoading()
            ticketItemListener.onActivationButtonPressed(currentItem)
        }
    }

    private fun setupTicketClickListener() {
        itemView.setOnClickListener { ticketItemListener.onTicketClick(currentItem) }
    }

    override fun bindItemWithPayload(item: TicketModel, payload: MutableList<Any>) {
        currentItem = item
        val bundle = payload[0] as Bundle
        if (bundle.getBoolean(ACTIVATION_CHANGED)) {
            setActivationButtonState()
        }
    }

    companion object {

        private const val ACTIVATION_CHANGED = "ACTIVATION"

        fun getActivationChangedPayload(): Bundle {
            val changesPayload = Bundle()
            changesPayload.putBoolean(ACTIVATION_CHANGED, true)
            return changesPayload
        }
    }
}