package com.saeed.projects.mycontacts.utils.rx;

import io.reactivex.Scheduler;

public interface SchedulerProvider
{
    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
