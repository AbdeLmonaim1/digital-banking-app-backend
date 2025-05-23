import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {jwtDecode} from 'jwt-decode';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  isAuthenticated: boolean = false;
  roles: any;
  username: any;
  accessToken: any;
  constructor(private http: HttpClient, private router: Router) { }
  public login(username: string, password: string){
    let options = {
      headers: new HttpHeaders().set("Content-Type", "application/x-www-form-urlencoded")
    }
    let params = new HttpParams().set("username", username).set("password", password);
    return this.http.post("http://localhost:8086/auth/login", params, options);
  }

  loadProfile(data: any) {
    this.isAuthenticated = true;
    this.accessToken = data['access-token'];
    let decodedJwt:any = jwtDecode(this.accessToken);
    this.username = decodedJwt.sub;
    this.roles = decodedJwt.scope;
    window.localStorage.setItem("jwt-tokent", this.accessToken);

  }

  logout() {
    this.isAuthenticated = false;
    this.accessToken = undefined;
    this.username = undefined;
    this.roles = undefined;
    window.localStorage.removeItem("jwt-tokent");
    this.router.navigateByUrl("/login");
  }

  loadJwtTokenFromLocalStorage() {
    let jwtToken = window.localStorage.getItem("jwt-tokent");
    if(jwtToken){
      this.loadProfile({"access-token": jwtToken});
      this.router.navigateByUrl('/admin/customers');
    }
  }
}
