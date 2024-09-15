package com.example.merqurinotes.notes.common.response

import com.example.merqurinotes.utils.json.JSON

data class RetrieveCategoryListResponse(
    var categoryList: List<CategoryList>,
) {
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
  "data":[
      {
        "category": "Work and study",
      },
      {
        "category": "Life",
      },
      {
        "category": "Healthy and well-being",
      }
    ]
}
"""