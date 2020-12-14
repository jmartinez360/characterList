package com.android.lidlapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.android.domain.model.TicketDetailDomainModel
import com.android.domain.model.TicketDomainModel
import com.android.domain.usecase.GetTicketDetail
import com.android.lidlapp.mapper.DomainToModelMapper
import com.android.lidlapp.model.TicketDetailModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class TicketDetailViewModel : BaseViewModel() {

    abstract fun getTicketDetail(ticketId: String)

    abstract val ticketDetailLiveData: LiveData<TicketDetailModel>
}

class TicketDetailViewModelImpl(
    private val getTicketDetail: GetTicketDetail,
    private val mapper: DomainToModelMapper
) : TicketDetailViewModel() {

    private val _ticketDetailLiveData = MediatorLiveData<TicketDetailModel>()
    override val ticketDetailLiveData: LiveData<TicketDetailModel>
        get() = _ticketDetailLiveData

    private val _loadingLiveData = MediatorLiveData<Boolean>()
    override val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    private val _errorLiveData = MediatorLiveData<Boolean>()
    override val errorLiveData: LiveData<Boolean>
        get() = _errorLiveData

    private lateinit var ticketId: String
    private var ticketDetail: TicketDetailModel? = null

    override fun getTicketDetail(ticketId: String) {
        this.ticketId = ticketId
        if (ticketDetail == null) {
            viewModelScope.launch { getTicketDetailSetup() }
        } else {
            showTicketDetail()
        }

    }

    private suspend fun getTicketDetailSetup() {
        showLoading()
        getTicketDetail.execute(ticketId)
            .catch { onError() }
            .collect { onResult(it) }
    }

    private fun showLoading() {
        _loadingLiveData.postValue(SHOW)
    }

    private fun hideLoading() {
        _loadingLiveData.postValue(HIDE)
    }

    private fun onError() {
        hideLoading()
        showError()
    }

    private fun showError() {
        _errorLiveData.postValue(SHOW)
    }

    private fun onResult(domainTicket: TicketDetailDomainModel) {
        hideLoading()
        mapDomainTicketToModelTicket(domainTicket)
        showTicketDetail()
    }

    private fun mapDomainTicketToModelTicket(ticket: TicketDetailDomainModel) {
        ticketDetail = mapper.mapToTicketDetailModel(ticket)
    }

    private fun showTicketDetail() {
        _ticketDetailLiveData.postValue(ticketDetail)
    }
}