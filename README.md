# MultiLevelCheckBox

### Presentation
![multi_level_check_box_test](https://user-images.githubusercontent.com/13168107/160270092-5b67d134-1b3d-4208-9d2b-264d3e2eaedd.gif)

### Usage
```xml
<com.raonstudio.multilevelcheckbox.MultiLevelCheckBox
    android:id="@+id/parent_checkbox"
    android:layout_width="wrap_content"
    android:layout_height="40dp"
    android:text="전체선택" />

<com.raonstudio.multilevelcheckbox.MultiLevelCheckBox
    android:id="@+id/checkbox1"
    android:layout_width="wrap_content"
    android:layout_height="36dp"
    android:layout_marginStart="24dp"
    android:text="옵션1"
    app:parentCheckBox="@id/parent_checkbox" />

<com.raonstudio.multilevelcheckbox.MultiLevelCheckBox
    android:id="@+id/checkbox2"
    android:layout_width="wrap_content"
    android:layout_height="36dp"
    android:layout_marginStart="48dp"
    android:text="옵션2"
    app:parentCheckBox="@id/parent_checkbox" />
```
add parentCheckBox property in xml with MultiLevelCheckBox

### How To Import
Add 2 files
1. values/attrs.xml
2. MultiLevelCheckBox.kt to any package
