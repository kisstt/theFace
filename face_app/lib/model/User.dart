class User {
  String username;
  String password;
  String tele;
  String nickname;

  void setUsername(String username) {
    this.username = username;
  }

  void setPassword(String password) {
    this.password = password;
  }

  void setNickname(String nickname) {
    this.nickname = nickname;
  }

  void setTele(String tele) {
    this.tele = tele;
  }

  String getTele() {
    return this.tele;
  }

  String getNickname() {
    return this.nickname;
  }

  String getPassword() {
    return this.password;
  }

  String getUsername() {
    return this.username;
  }
}
