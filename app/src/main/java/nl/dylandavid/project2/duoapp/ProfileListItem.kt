package nl.dylandavid.project2.duoapp

import java.util.ArrayList

class ProfileListItem(var Title: String,var ItemValue: String?,var Abbreviation: String?, var ChildItems : MutableList<ProfileListChildItem>?) {
    override fun toString(): String {
        return Title
    }
}
class ProfileListChildItem(var Title: String,var ItemValue: String,var Abbreviation: String) {

}