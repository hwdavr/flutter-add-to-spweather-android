import 'package:async/async.dart';
import 'package:flutter/material.dart';
import 'package:flutter_module/model/checkable_city.dart';
import 'package:flutter_module/service/cities_service.dart';
import 'package:flutter_module/model/cities.dart';
import 'package:chopper/chopper.dart';
import 'package:provider/provider.dart';

class CheckableCityListView extends StatefulWidget {
  final List<CheckableCity> checkableCities;

  CheckableCityListView(this.checkableCities);

  @override
  _CheckableCityListViewState createState() => _CheckableCityListViewState();
}

class _CheckableCityListViewState extends State<CheckableCityListView> {
  // AsyncMemoizer<Response<PopularCities>> _memoizer;

  @override
  void initState() {
    super.initState();
    // _memoizer = AsyncMemoizer<Response<PopularCities>>();
  }

  // Future<Response<PopularCities>> _getPopularCities() async {
  //   return this._memoizer.runOnce(() async {
  //     return Provider.of<CitiesService>(context).getPopularCities();
  //   });
  // }

  FutureBuilder<Response<PopularCities>> _buildBody(
      BuildContext context, List<CheckableCity> checkableCities) {
    return FutureBuilder<Response<PopularCities>>(
      future: Provider.of<CitiesService>(context).getPopularCities(),
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.done) {
          if (snapshot.hasError) {
            return Center(
              child: Text(
                snapshot.error.toString(),
                textAlign: TextAlign.center,
                textScaleFactor: 1.3,
              ),
            );
          }

          final popular = snapshot.data.body;
          // Remove previous items, other hot reload will add all the items again
          checkableCities.clear();
          popular.data.forEach((element) {
            checkableCities.add(CheckableCity(element));
          });
          return _buildCityList(context, checkableCities);
        } else {
          // Show a loading indicator while waiting for the movies
          return Center(
            child: CircularProgressIndicator(),
          );
        }
      },
    );
  }

  _buildCityList(BuildContext context, List<CheckableCity> cities) {
    // Wrap with StatefulBuilder, to prevent from refreshing the whole UI
    return StatefulBuilder(builder: (BuildContext context, setState) {
      return ListView.builder(
        itemCount: cities.length,
        itemBuilder: (context, index) {
          return CheckboxListTile(
            title: Text(cities[index].city),
            value: cities[index].isChecked,
            onChanged: (val) {
              setState(
                () {
                  cities[index].isChecked = val;
                },
              );
            },
          );
        },
      );
    });
  }

  @override
  Widget build(BuildContext context) {
    return _buildBody(context, widget.checkableCities);
  }
}
