import 'package:flutter/material.dart';

class User extends ChangeNotifier {
  bool isAuthenticated = false;
  String displayName = '';

  void signIn() {
    isAuthenticated = true;
    displayName = 'John Doe';
    notifyListeners();
  }
}
