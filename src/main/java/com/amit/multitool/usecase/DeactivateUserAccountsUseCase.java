package com.amit.multitool.usecase;

import java.util.List;

public interface DeactivateUserAccountsUseCase {

    void deactivateUserAccounts(List<String> emails);

}
