package com.nadji.myapp.presentation.main.home

import android.content.Context
import android.content.res.AssetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadji.myapp.common.utils.Constant
import com.nadji.myapp.common.wrapper.Resource
import com.nadji.myapp.data.local.entity.HistoryEntity
import com.nadji.myapp.data.local.entity.UserEntity
import com.nadji.myapp.data.network.model.DataItems
import com.nadji.myapp.data.network.model.PortofolioResponse
import com.nadji.myapp.data.network.model.PortofolioResponseItem
import com.nadji.myapp.data.network.model.PromoEntity
import com.nadji.myapp.data.network.model.PromoResponse
import com.nadji.myapp.data.repository.LocalRepository
import com.nadji.myapp.data.repository.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val localRepository: LocalRepository
) : HomeContract, ViewModel() {
    private val _getUserResult = MutableStateFlow<Resource<List<UserEntity>>>(Resource.Empty())
    override val getUserResult: StateFlow<Resource<List<UserEntity>>> = _getUserResult

    private val _getUserByIdResult = MutableStateFlow<Resource<UserEntity>>(Resource.Empty())
    override val getUserByIdResult: StateFlow<Resource<UserEntity>> = _getUserByIdResult

    private val _insertUserResult = MutableStateFlow<Resource<Unit>>(Resource.Empty())
    override val insertUserResult: StateFlow<Resource<Unit>> = _insertUserResult

    private val _insertHistoryResult = MutableStateFlow<Resource<Unit>>(Resource.Empty())
    override val insertHistoryResult: StateFlow<Resource<Unit>> = _insertHistoryResult

    private val _getHistoryResult =
        MutableStateFlow<Resource<List<HistoryEntity>>>(Resource.Empty())
    override val getHistoryResult: StateFlow<Resource<List<HistoryEntity>>> = _getHistoryResult

    private val _promoResult = MutableStateFlow<Resource<PromoResponse>>(Resource.Empty())
    override val promoResult: StateFlow<Resource<PromoResponse>> = _promoResult

    private val _insertPromoResult = MutableStateFlow<Resource<Unit>>(Resource.Empty())
    override val insertPromoResult: StateFlow<Resource<Unit>> = _insertPromoResult

    private val _getPromoResult = MutableStateFlow<Resource<List<PromoEntity>>>(Resource.Empty())
    override val getPromoResult: StateFlow<Resource<List<PromoEntity>>> = _getPromoResult

    private val _deleteResult = MutableStateFlow<Resource<Unit>>(Resource.Empty())
    override val deleteResult: StateFlow<Resource<Unit>> = _deleteResult

    private val portofolioData = MutableLiveData<PortofolioResponse>()
    fun getPortofolioData(): LiveData<PortofolioResponse> {
        return portofolioData
    }

    override fun insertUser(user: UserEntity) {
        _insertUserResult.value = Resource.Loading()
        viewModelScope.launch {
            localRepository.insertUser(user).collect {
                try {
                    _insertUserResult.value = Resource.Success(it.data)
                } catch (e: Exception) {
                    _insertUserResult.value = Resource.Error(
                        exception = e, message = Constant.ERROR_MESSAGE
                    )
                }
            }
        }
    }

    override fun updateUser(user: String, field: String) {
        _insertUserResult.value = Resource.Loading()
        viewModelScope.launch {
            localRepository.updateUser(user, field).collect {
                try {
                    _insertUserResult.value = Resource.Success(it.data)
                } catch (e: Exception) {
                    _insertUserResult.value = Resource.Error(
                        exception = e, message = Constant.ERROR_MESSAGE
                    )
                }
            }
        }
    }


    override fun getUser() {
        _getUserResult.value = Resource.Loading()
        viewModelScope.launch {
            localRepository.getUser().collect {
                try {
                    _getUserResult.value = Resource.Success(it.data)
                } catch (e: Exception) {
                    _getUserResult.value = Resource.Error(
                        exception = e, message = Constant.ERROR_MESSAGE
                    )
                }
            }
        }
    }

    override fun getUserById(id: String) {
        _getUserByIdResult.value = Resource.Loading()
        viewModelScope.launch {
            localRepository.getUserById(id).collect {
                try {
                    _getUserByIdResult.value = Resource.Success(it.data)
                } catch (e: Exception) {
                    _getUserResult.value = Resource.Error(
                        exception = e, message = Constant.ERROR_MESSAGE
                    )
                }
            }
        }
    }

    override fun insertHistory(history: HistoryEntity) {
        _insertHistoryResult.value = Resource.Loading()
        viewModelScope.launch {
            localRepository.insertHistory(history).collect {
                try {
                    _insertHistoryResult.value = Resource.Success(it.data)
                } catch (e: Exception) {
                    _insertHistoryResult.value = Resource.Error(
                        exception = e, message = Constant.ERROR_MESSAGE
                    )
                }
            }
        }
    }

    override fun getHistory() {
        _getHistoryResult.value = Resource.Loading()
        viewModelScope.launch {
            localRepository.getHistory().collect {
                try {
                    _getHistoryResult.value = Resource.Success(it.data)
                } catch (e: Exception) {
                    _getHistoryResult.value = Resource.Error(
                        exception = e, message = Constant.ERROR_MESSAGE
                    )
                }
            }
        }
    }

    override fun promo() {
        _promoResult.value = Resource.Loading()
        viewModelScope.launch {
            networkRepository.promo()
                .collect {
                    try {
                        _promoResult.value = Resource.Success(it.data)
                    } catch (e: Exception) {
                        _promoResult.value = Resource.Error(
                            exception = e,
                            message = Constant.ERROR_MESSAGE
                        )
                    }
                }
        }
    }

    override fun insertPromo(promoEntity: PromoEntity) {
        _insertPromoResult.value = Resource.Loading()
        viewModelScope.launch {
            localRepository.insertPromo(promoEntity).collect {
                try {
                    _insertPromoResult.value = Resource.Success(it.data)
                } catch (e: Exception) {
                    _insertPromoResult.value = Resource.Error(
                        exception = e, message = Constant.ERROR_MESSAGE
                    )
                }
            }
        }
    }

    override fun getPromo() {
        _getPromoResult.value = Resource.Loading()
        viewModelScope.launch {
            localRepository.getPromo().collect {
                try {
                    _getPromoResult.value = Resource.Success(it.data)
                } catch (e: Exception) {
                    _getPromoResult.value = Resource.Error(
                        exception = e, message = Constant.ERROR_MESSAGE
                    )
                }
            }
        }
    }

    override fun deletePromo() {
        _deleteResult.value = Resource.Loading()
        viewModelScope.launch {
            localRepository.deletePromo()
                .collect {
                    try {
                        _deleteResult.value = Resource.Success(it.data)
                    } catch (e: Exception) {
                        _deleteResult.value = Resource.Error(
                            exception = e,
                            message = Constant.ERROR_MESSAGE
                        )
                    }
                }
        }
    }

    fun readPortofolioDataFromAssets(context: Context) {
        val assetManager = context.assets
        viewModelScope.launch {
            try {
                val inputStream = assetManager.open("data.json")
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                val json = String(buffer, Charsets.UTF_8)
                val portofolioResponse = parseJson(json)

                portofolioData.value = portofolioResponse
            } catch (e: IOException) {
            }
        }
    }

    fun fetchPortofolioDataFromAssets(assetManager: AssetManager) {
        try {
            val inputStream = assetManager.open("data.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val json = String(buffer, Charsets.UTF_8)
            val portofolioResponse = parseJson(json)

            portofolioData.postValue(portofolioResponse)
        } catch (e: IOException) {
        }
    }

    private fun parseJson(json: String): PortofolioResponse {
        try {
            val jsonArray = JSONArray(json)
            val portofolioList = mutableListOf<PortofolioResponseItem>()

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val type = jsonObject.getString("type")
                val data = jsonObject.getJSONArray("data")

                val dataItemsList = mutableListOf<DataItems>()

                for (j in 0 until data.length()) {
                    val dataItemObject = data.getJSONObject(j)
                    val percentage = dataItemObject.getString("percentage")
                    val label = dataItemObject.getString("label")
                    val data = dataItemObject.getJSONArray("data")

                    for (k in 0 until data.length()) {
                        val dataItemss = data.getJSONObject(k)
                        val trxDate = dataItemss.getString("trx_date")
                        val nominal = dataItemss.getInt("nominal")
                        val dataItem = DataItems(null, percentage, label, nominal, trxDate)
                        dataItemsList.add(dataItem)
                    }
                }

                val portofolioResponseItem = PortofolioResponseItem(dataItemsList, type)
                portofolioList.add(portofolioResponseItem)
            }

            return PortofolioResponse(portofolioList)
        } catch (e: JSONException) {
            return PortofolioResponse(null)
        }
    }

    init {
        getUser()
        getPromo()
        getHistory()
    }

}