package com.example.mealdealapp.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealdealapp.Event
import com.example.mealdealapp.R
import com.skydoves.landscapist.coil.CoilImage

/**
 * Created by shanta on 5/3/24
 */


abstract class SectionedListItem<Action: Any> {

    open fun itemKey(): Int = hashCode()
    open fun sectionMatcher(): String? = null

    @Composable
    open fun HeaderItem(){
        // by default no header
    }

    @Composable
    abstract fun SectionItem(onItemClick: (Action) -> Unit)
}

fun <Action:Any>LazyListScope.sectionedList(
    items: List<SectionedListItem<Action>>,
    onItemClick: (Action) -> Unit
){
    items.forEachIndexed{ index, listItem ->

        if(index == 0 || listItem.sectionMatcher() != items[index-1].sectionMatcher()){

            item{
                listItem.HeaderItem()
            }

        }

        item(
            key = listItem.itemKey(),
            contentType = listItem::class
        ){
            listItem.SectionItem(onItemClick)
        }

    }
}

data class MealDealItem(
    val imageUrl: String?,
    val title: String,
    val id: String,
    val categoryId: String,
    val categoryName: String,
): SectionedListItem<Event>() {

    override fun sectionMatcher(): String = categoryId

    @Composable
    override fun SectionItem(onItemClick: (Event) -> Unit) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clickable { onItemClick(Event.ViewItemDetails(id, categoryId))},
            verticalAlignment = Alignment.CenterVertically) {

            CoilImage(
                modifier = Modifier.size(60.dp),
                imageModel = { imageUrl },
                failure = {
                    Image(painter = painterResource(id = R.drawable.placeholder),
                        contentDescription = null)
                }
            )
            
            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = title,
                color = Color.Gray,
                fontSize = 22.sp
            )


        }
        HorizontalDivider()
    }

    @Composable
    override fun HeaderItem() {
        Row(
            Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = categoryName,
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }

        HorizontalDivider()
    }
}