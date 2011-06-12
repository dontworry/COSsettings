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

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.provider.Settings;

public class DisplayActivity extends PreferenceActivity implements OnPreferenceChangeListener {

    private static final String GENERAL_CATEGORY = "general_category";

    /* Other */
    private static final String ROTATION_90_PREF = "pref_rotation_90";
    private static final String ROTATION_180_PREF = "pref_rotation_180";
    private static final String ROTATION_270_PREF = "pref_rotation_270";

    private CheckBoxPreference mRotation90Pref;
    private CheckBoxPreference mRotation180Pref;
    private CheckBoxPreference mRotation270Pref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.display_settings_title_subhead);
        addPreferencesFromResource(R.xml.display_settings);

        PreferenceScreen prefSet = getPreferenceScreen();
      
        /* Rotate 180 */
	        mRotation90Pref = (CheckBoxPreference) prefSet.findPreference(ROTATION_90_PREF);
	        mRotation180Pref = (CheckBoxPreference) prefSet.findPreference(ROTATION_180_PREF);
	        mRotation270Pref = (CheckBoxPreference) prefSet.findPreference(ROTATION_270_PREF);
	        int mode = Settings.System.getInt(getContentResolver(),
	                        Settings.System.ACCELEROMETER_ROTATION_MODE, 5);
	        mRotation90Pref.setChecked((mode & 1) != 0);
0	        mRotation180Pref.setChecked((mode & 2) != 0);
	        mRotation270Pref.setChecked((mode & 4) != 0);
    }

    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;

        /* Preference Screens */
        if (preference == mRotation90Pref ||
            preference == mRotation180Pref ||
            preference == mRotation270Pref) {
            int mode = 0;
            if (mRotation90Pref.isChecked()) mode |= 1;
            if (mRotation180Pref.isChecked()) mode |= 2;
            if (mRotation270Pref.isChecked()) mode |= 4;
            Settings.System.putInt(getContentResolver(),
                     Settings.System.ACCELEROMETER_ROTATION_MODE, mode);
        }

        return true;
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

}
