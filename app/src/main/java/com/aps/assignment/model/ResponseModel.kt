package com.aps.assignment.model

import java.io.Serializable

data class ResponseModel(
    var apps: ArrayList<App>
) {
    data class App(
        var currency: String,
        var `data`: Data,
        var money_format: String,
        var name: String
    ):Serializable {
        data class Data(
            var add_to_cart: AddToCart,
            var downloads: Downloads,
            var sessions: Sessions,
            var total_sale: TotalSale
        ):Serializable {
            data class AddToCart(
                var month_wise: MonthWise,
                var total: Int
            ):Serializable {
                data class MonthWise(
                    var apr: Int,
                    var feb: Int,
                    var jan: Int,
                    var jun: Int,
                    var mar: Int,
                    var may: Int
                ):Serializable
            }

            data class Downloads(
                var month_wise: MonthWise,
                var total: Int
            ) :Serializable{
                data class MonthWise(
                    var apr: Int,
                    var feb: Int,
                    var jan: Int,
                    var jun: Int,
                    var mar: Int,
                    var may: Int
                ):Serializable
            }

            data class Sessions(
                var month_wise: MonthWise,
                var total: Int
            ) :Serializable{
                data class MonthWise(
                    var apr: Int,
                    var feb: Int,
                    var jan: Int,
                    var jun: Int,
                    var mar: Int,
                    var may: Int
                ):Serializable
            }

            data class TotalSale(
                var month_wise: MonthWise,
                var total: Int
            ):Serializable {
                data class MonthWise(
                    var apr: Int,
                    var feb: Int,
                    var jan: Int,
                    var jun: Int,
                    var mar: Int,
                    var may: Int
                ):Serializable
            }
        }
    }
}