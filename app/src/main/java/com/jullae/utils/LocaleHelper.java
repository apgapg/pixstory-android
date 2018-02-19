package com.jullae.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import java.util.Locale;

/**
 * Created by Rahul Abrol on 13/12/17.
 * Class used to handle language configuraton.
 */
public class LocaleHelper extends ContextWrapper {
    /**
     * Constructor.
     *
     * @param base context.
     */
    public LocaleHelper(final Context base) {
        super(base);
    }

    /**
     * Method used to wrap Current context with new one.
     *
     * @param c         context
     * @param newLocale lang.
     * @return Context
     */
    public static ContextWrapper wrap(final Context c, final Locale newLocale) {
        Context context = c;
        Resources res = context.getResources();
        Configuration configuration = res.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(newLocale);

            LocaleList localeList = new LocaleList(newLocale);
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);

            context = context.createConfigurationContext(configuration);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            configuration.setLocale(newLocale);
            context = context.createConfigurationContext(configuration);

        } else {
            configuration.locale = newLocale;
            res.updateConfiguration(configuration, res.getDisplayMetrics());
        }

        return new LocaleHelper(context);
    }
}