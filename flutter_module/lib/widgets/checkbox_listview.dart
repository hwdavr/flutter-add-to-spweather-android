import 'package:flutter/material.dart';
import 'package:flutter_module/model/checkable_city.dart';

class CheckBoxInListView extends StatefulWidget {
  final List<CheckableCity> cities;

  CheckBoxInListView(this.cities);

  @override
  _CheckBoxInListViewState createState() => _CheckBoxInListViewState();
}

class _CheckBoxInListViewState extends State<CheckBoxInListView> {

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: widget.cities.length,
      itemBuilder: (context, index) {
        return CheckboxListTile(
          title: Text(widget.cities[index].city),
          value: widget.cities[index].isChecked,
          onChanged: (val) {
            setState(
                  () { widget.cities[index].isChecked = val;},
            );
          },
        );
      },
    );
  }
}
