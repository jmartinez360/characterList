package com.android.lidlapp.ui.recycler.listener

import com.android.lidlapp.model.TicketModel

interface TicketItemListener {

    fun onActivationButtonPressed(ticket: TicketModel)
    fun onTicketClick(ticket: TicketModel)
}