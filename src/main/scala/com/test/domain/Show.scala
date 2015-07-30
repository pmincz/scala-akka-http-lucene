package com.test.domain

/**
 * Created by pmincz on 7/26/15.
 */
case class Show(id: Long, original_name: String, name: String, overview: String, popularity: Double, vote_average: Double, vote_count: Int)

case class ShowResult(results: List[Show])