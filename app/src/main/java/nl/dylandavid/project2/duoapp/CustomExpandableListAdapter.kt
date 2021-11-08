package nl.dylandavid.project2.duoapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView

class CustomExpandableListAdapter(
    var context: Context,
    val items: MutableList<ProfileListItem>,

) : BaseExpandableListAdapter() {
    override fun getGroupCount(): Int {
        return items.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        if(items[groupPosition].ChildItems == null){
            return 0
        }
       return items[groupPosition].ChildItems!!.size
    }

    override fun getGroup(groupPosition: Int): ProfileListItem {
        return items[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): ProfileListChildItem {
        return items[groupPosition].ChildItems!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
       var convertView = convertView
        var item = getGroup(groupPosition)
        if (convertView == null){
            val infalInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.profile_group_item, null)
        }

        var title = convertView?.findViewById<TextView>(R.id.tvItemTitle)
        var imgIndicator = convertView!!.findViewById<ImageView>(R.id.imgExpanderIcon)
        var itemVal = convertView?.findViewById<TextView>(R.id.tvItemVal)
        var abbr = convertView?.findViewById<TextView>(R.id.tvItemAbbr)

        if (title != null) {
            title.text = item.Title
        }

        if (item.ChildItems == null) {
            imgIndicator.visibility = View.GONE
            itemVal.visibility = View.VISIBLE
            abbr.visibility = View.VISIBLE
            if (itemVal != null) {
                itemVal.text = item.ItemValue
            }
            if (abbr != null) {
                abbr.text = item.Abbreviation
            }
        }else {
            imgIndicator.visibility = View.VISIBLE
            itemVal.visibility = View.GONE
            abbr.visibility = View.GONE
            if (isExpanded) {
                imgIndicator.setImageResource(R.drawable.chevron_up);
            } else {
                imgIndicator.setImageResource(R.drawable.chevron_down);
            }
        }

        return convertView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var convertView = convertView
        var itemChild:ProfileListChildItem = getChild(groupPosition, childPosition)
        if (convertView == null){
            val infalInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = infalInflater.inflate(R.layout.profile_child_item, null)
        }

        var txtTitle = convertView!!.findViewById<TextView>(R.id.tvChildItemTitle)
        var txtValue = convertView!!.findViewById<TextView>(R.id.tvChildVal)
        var txtAbbr = convertView!!.findViewById<TextView>(R.id.tvChildAbbr)
        txtTitle.text = itemChild.Title
        txtValue.text = itemChild.ItemValue
        txtAbbr.text = itemChild.Abbreviation

        return convertView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}