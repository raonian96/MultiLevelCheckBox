package com.raonstudio.multilevelcheckbox

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.checkbox.MaterialCheckBox


class MultiLevelCheckBox(context: Context, attributeSet: AttributeSet) :
    MaterialCheckBox(context, attributeSet) {
    private val parentId: Int
    private val optional: Boolean
    private val parentCheckBox get() = rootView.findViewById<MultiLevelCheckBox>(parentId)

    private val checkedChildrenId = mutableListOf<Int>()
    private val childrenId = mutableListOf<Int>()
    private val childrenCheckBox get() = childrenId.map { rootView.findViewById<MultiLevelCheckBox>(it) }

    init {
        context.theme.obtainStyledAttributes(
            attributeSet, R.styleable.MultiLevelCheckBox, 0, 0
        ).apply {
            try {
                parentId = getResourceId(R.styleable.MultiLevelCheckBox_parentCheckBox, -1)
                optional = getBoolean(R.styleable.MultiLevelCheckBox_optional, false)
            } finally {
                recycle()
            }
        }
    }

    val isValid: Boolean
        get() {
            if (isChecked) return true
            else if (!isChecked && optional) return true
            else if (childrenCheckBox.isEmpty()) return false
            var isChildrenValid = true
            for (child in childrenCheckBox) {
                if (!child.isValid) {
                    isChildrenValid = false
                    break
                }
            }
            return isChildrenValid
        }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        parentCheckBox?.childrenId?.add(id)
    }

    override fun performClick(): Boolean {
        val willBeChecked = !isChecked
        updateChildren(willBeChecked)
        updateParent(willBeChecked)
        return super.performClick()
    }

    private fun updateChildren(checked: Boolean) {
        childrenCheckBox.forEach {
            it.isChecked = checked
            it.updateChildren(checked)
        }
        with(checkedChildrenId) {
            if (checked) {
                clear()
                addAll(childrenId)
            } else {
                clear()
            }
        }
    }

    private fun updateParent(checked: Boolean) {
        parentCheckBox?.notifyChildChange(id, checked)
    }

    private fun notifyChildChange(childCheckBoxId: Int, checked: Boolean) {
        with(checkedChildrenId) {
            if (checked) {
                add(childCheckBoxId)
                takeIf { size == childrenId.size }?.let {
                    isChecked = true
                    updateParent(true)
                }
            } else {
                remove(childCheckBoxId)
                isChecked = false
                updateParent(false)
            }
        }
    }
}