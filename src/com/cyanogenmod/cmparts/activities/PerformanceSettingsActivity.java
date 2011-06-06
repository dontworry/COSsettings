/*
 * Copyright (C) 2011 The CyanogenMod Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cyanogenmod.cmparts.activities;

import com.cyanogenmod.cmparts.R;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemProperties;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import java.io.File;

/**
 * Performance Settings
 */
public class PerformanceSettingsActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    private static final String GENERAL_CATEGORY = "general_category";

    private static final String JIT_PREF = "pref_jit_mode";

    private static final String JIT_ENABLED = "int:jit";

    private static final String JIT_DISABLED = "int:fast";

    private static final String JIT_PERSIST_PROP = "persist.sys.jit-mode";

    private static final String JIT_PROP = "dalvik.vm.execution-mode";

    private static final String HEAPSIZE_PREF = "pref_heapsize";

    private static final String HEAPSIZE_PROP = "dalvik.vm.heapsize";

    private static final String HEAPSIZE_PERSIST_PROP = "persist.sys.vm.heapsize";

    private static final String HEAPSIZE_DEFAULT = "16m";

    private CheckBoxPreference mJitPref;

    private ListPreference mHeapsizePref;

    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.performance_settings_title_subhead);
        addPreferencesFromResource(R.xml.performance_settings);

        PreferenceScreen prefSet = getPreferenceScreen();
        
        PreferenceCategory generalCategory = (PreferenceCategory)prefSet.findPreference(GENERAL_CATEGORY);

        mJitPref = (CheckBoxPreference) prefSet.findPreference(JIT_PREF);
        String jitMode = SystemProperties.get(JIT_PERSIST_PROP,
                SystemProperties.get(JIT_PROP, JIT_ENABLED));
        mJitPref.setChecked(JIT_ENABLED.equals(jitMode));

        mHeapsizePref = (ListPreference) prefSet.findPreference(HEAPSIZE_PREF);
        mHeapsizePref.setValue(SystemProperties.get(HEAPSIZE_PERSIST_PROP,
                SystemProperties.get(HEAPSIZE_PROP, HEAPSIZE_DEFAULT)));
        mHeapsizePref.setOnPreferenceChangeListener(this);

        // Set up the warning
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.performance_settings_warning_title);
        alertDialog.setMessage(getResources().getString(R.string.performance_settings_warning));
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                getResources().getString(com.android.internal.R.string.ok),
                new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        alertDialog.show();
    }
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
	if (preference == mJitPref) {
            SystemProperties.set(JIT_PERSIST_PROP,
                    mJitPref.isChecked() ? JIT_ENABLED : JIT_DISABLED);
            return true;
        }

    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mHeapsizePref) {
            if (newValue != null) {
                SystemProperties.set(HEAPSIZE_PERSIST_PROP, (String)newValue);
                return true;
            }
        }

        return false;
    }

}
