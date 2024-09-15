package com.example.merqurinotes.notes.common.response

import com.example.merqurinotes.utils.json.JSON

data class RetrieveCategoryListResponse(
    var categoryList: List<CategoryList>,
){
    companion object {
        @JvmStatic
        fun createTestData(): RetrieveCategoryListResponse {
            return JSON.toObject(TEST_JSON, RetrieveCategoryListResponse::class.java)
        }
    }
}


data class CategoryList(
    var category: String = ""
)

private const val TEST_JSON = """
{
  "responseData": {
    "banks": [
      {
        "bankCode": "XYZBANKXXXX",
        "bankName": "XYZ Bank",
        "segment": "Retail"
      },
      {
        "bankCode": "ABCBANKXXXX",
        "bankName": "ABC Bank",
        "segment": "Retail"
      },
      {
        "bankCode": "ROBBANKXXXX",
        "bankName": "Robinsons Bank",
        "segment": "Retail"
      }
    ]
  }
}
"""