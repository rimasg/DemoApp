package com.sid.demoapp.databinding;

/**
 * Created by Okis on 2016.05.26 @ 11:16.
 */
public class ClickHandlers {
    private com.sid.demoapp.databinding.ActivityDataBindingBinding binding;

    public ClickHandlers(com.sid.demoapp.databinding.ActivityDataBindingBinding binding) {
        this.binding = binding;
    }

    public void changeUser() {
        binding.setUser(UserGenerator.generateUser());
    }
}
