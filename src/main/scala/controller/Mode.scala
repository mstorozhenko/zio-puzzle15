package com.wix.interview
package controller

sealed trait Mode
case object Continue extends Mode
case object Terminate extends Mode
case object Win extends Mode
