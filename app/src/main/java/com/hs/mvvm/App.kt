package com.hs.mvvm

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.hs.mvvm.app.Constants
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.bugly.beta.Beta
import timber.log.Timber

@Suppress("UNREACHABLE_CODE")
class App : Application() {

    init {
        //设置全局的Header构建器
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
//            @Override
//            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
//                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
//            }
//        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.colorPrimary,R.color.white)
             MaterialHeader(context)
        }

        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            return@setDefaultRefreshFooterCreator ClassicsFooter(context)
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
        Beta.installTinker()
    }


    override fun onCreate() {
        super.onCreate()
        initLogger()
    }

    private fun initLogger() {
        TODO("Not yet implemented")
        var formatStrategy = PrettyFormatStrategy.newBuilder()
            .methodOffset(5)
            .tag(Constants.TAG)
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))

        Timber.plant(object : Timber.DebugTree(){
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                super.log(priority, tag, message, t)
                if (BuildConfig.DEBUG){
                    Logger.log(priority,tag,message,t)
                }
            }
        })

    }
}