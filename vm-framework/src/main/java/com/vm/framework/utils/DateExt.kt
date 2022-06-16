package com.vm.framework.utils

import org.joda.time.DateTime


val DateTime.dayMonthYear: String get() = toString("d MMMM, yyyy")