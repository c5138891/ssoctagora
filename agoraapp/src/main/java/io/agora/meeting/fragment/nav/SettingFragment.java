package io.agora.meeting.fragment.nav;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import io.agora.meeting.R;
import io.agora.meeting.base.AppBarDelegate;
import io.agora.meeting.base.BaseFragment;

public class SettingFragment extends PreferenceFragmentCompat implements AppBarDelegate {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setDivider(null);
        getToolbar().setTitle(R.string.setting);
        BaseFragment.setupActionBar(this);
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.setting_preferences, rootKey);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        return super.onPreferenceTreeClick(preference);
    }

    @Override
    public Toolbar getToolbar() {
        return requireView().findViewById(R.id.toolbar);
    }

    @Override
    public boolean lightMode() {
        return false;
    }
}
