package com.mauk.absen.adapter


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log.e
import android.util.Log.w
import android.view.*
import android.widget.*
import com.mauk.absen.R
import com.mauk.absen.model.Tugas
import com.mauk.absen.utils.ProgressButton
import com.mauk.absen.view.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import zlc.season.rxdownload4.download
import zlc.season.rxdownload4.manager.*
import zlc.season.rxdownload4.notification.SimpleNotificationCreator
import zlc.season.rxdownload4.utils.safeDispose


class TugasAdapter: RecyclerView.Adapter<TugasAdapter.TugasViewHolder> {


    lateinit var listTugas:List<Tugas>
    lateinit var mContext : Context

    private var disposable: Disposable? = null
    private var state = Normal
    private var url = ""
    private var tag: Any? = null
    companion object{
        const val Normal = 0
        const val Started = 1
        const val Paused = 2
        const val Completed = 3
        const val Failed = 4
    }

    constructor(mContext: Context, listTugas: List<Tugas>){
        this.listTugas = listTugas
        this.mContext = mContext
    }
    @SuppressLint("NewApi")
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TugasViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_tugas, p0, false)
        val tugas = listTugas[p1]
        url = "http://35.240.167.224/tugass/${tugas.file}"
        return TugasViewHolder(view)
    }
    private fun OnClick(p0: TugasViewHolder){
        val task = url.manager()
        when(task.currentStatus()){
            is Normal-> task.start()
            is Started-> task.stop()
            is Downloading-> task.stop()
            is Failed -> task.start()
            is Paused -> task.start()
            is Completed -> Toast.makeText(mContext, "Telah di Download", Toast.LENGTH_SHORT).show()
            is Deleted -> task.start()
            else-> ""
        }
    }
    private fun start(p0: TugasViewHolder){

        w("QWO", "$url")
        disposable = url.download().observeOn(AndroidSchedulers.mainThread()).subscribeBy(
            onNext = {
                p0!!.file.text ="${it.downloadSizeStr()}/${it.totalSizeStr()}"
                p0!!.file.setProgress(it)
            },
            onComplete = {
                state = Completed
                p0!!.file.text = "Telah Didownload"
            },
            onError = {
                state = Failed
                w("tag", "${it.message}")
                p0!!.file.text = " Gagal Download"
            }
        )
        state = Started
        p0!!.file.text = "Mengunduh...."
    }
    private fun stop(p0: TugasViewHolder){
        state = Paused
        disposable.safeDispose()
        p0.file.text = "Lanjutkan"
            }

    override fun getItemCount(): Int {
        return listTugas.size
    }

    override fun onBindViewHolder(p0: TugasViewHolder, p1: Int) {
       val tugas = listTugas[p1]
        p0.nama.text = "Tugas Dari: "+ tugas.nama_guru
        p0.tugas.text = Html.fromHtml(tugas.tugas)
        p0.kelas.text = "Kelas :"+tugas.kelas.nama_kelas
        w("QWO", "$url")
        url = "http://35.240.167.224/tugass/${tugas.file}"
        e("tag", "e$url")
        if(tugas.file != "0"){
            p0.file.setOnClickListener {
                OnClick(p0)
                val task = url.manager(notificationCreator = SimpleNotificationCreator())
                val current = task.currentStatus()
                p0.file.setStatus(current)
                p0.file.text = stateStr(mContext, current)
                tag = task.subscribe {
                    p0.file.setStatus(it)
                    p0.file.text = stateStr(mContext, it)
                }
            }
        }else{
            p0.file.visibility = View.GONE
        }



    }

    private fun stateStr(context: Context, status: Status): String {
        return when(status){
            is Normal -> "Download"
            is Started -> "Paused"
            is Downloading -> "Paused"
            is Completed -> "Berhasil Di Download"
            is Paused -> "Lanjutkan Download"
            is Failed-> "Ulangi"
            is Deleted -> "File Di Delete"
            else -> ""
        }
    }

    class TugasViewHolder(item: View): RecyclerView.ViewHolder(item) {
        var nama : TextView
        var tugas : TextView
        var kelas: TextView
        var file : ProgressButton
        init {
            nama = item.findViewById(R.id.nama)
            tugas = item.findViewById(R.id.tugas)
            file = item.findViewById(R.id.file)
            kelas = item.findViewById(R.id.kelas)
        }
    }



}