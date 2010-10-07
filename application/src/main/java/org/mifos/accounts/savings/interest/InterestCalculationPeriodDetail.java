/*
 * Copyright (c) 2005-2010 Grameen Foundation USA
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */

package org.mifos.accounts.savings.interest;

import java.util.List;

import org.joda.time.Days;
import org.mifos.application.master.business.MifosCurrency;
import org.mifos.framework.util.helpers.Money;

/**
 * I represent a valid Interest Calculation Period.
 *
 * In mifos, savings interest calculation is to occur every x days/months.
 */
public class InterestCalculationPeriodDetail {

    private final InterestCalculationInterval interval;
    private final List<EndOfDayDetail> dailyDetails;
    private final Money minBalanceRequired;
    private final MifosCurrency currency;
    private final Double interestRate;
    private final Money balanceBeforeInterval;

    public InterestCalculationPeriodDetail(InterestCalculationInterval interval, List<EndOfDayDetail> dailyDetails, Money minBalanceRequired, Money balanceBeforeInterval, MifosCurrency currency, Double interestRate, Boolean isFirstActivityBeforeInterval) {
        this.dailyDetails = dailyDetails;
        this.minBalanceRequired = minBalanceRequired;
        this.currency = currency;
        this.interestRate = interestRate;
        this.balanceBeforeInterval = balanceBeforeInterval;
        if(isFirstActivityBeforeInterval || dailyDetails == null || dailyDetails.isEmpty()) {
            this.interval = interval;
        } else {
            this.interval = new InterestCalculationInterval(dailyDetails.get(0).getDate(),interval.getEndDate());
        }
    }

    public InterestCalculationInterval getInterval() {
        return this.interval;
    }

    public List<EndOfDayDetail> getDailyDetails() {
        return this.dailyDetails;
    }

    public boolean isMinimumBalanceReached(Money principal) {
        return principal.isGreaterThanOrEqual(minBalanceRequired);
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public int getDuration() {
        return Days.daysBetween(interval.getStartDate(), interval.getEndDate()).getDays();
    }

    public Money zeroAmount() {
        return new Money(currency, "0");
    }

    public Money getBalanceBeforeInterval() {
        return balanceBeforeInterval;
    }
}