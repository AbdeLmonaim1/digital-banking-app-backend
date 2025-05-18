import {Component, ElementRef, HostListener, OnInit, QueryList, ViewChildren} from '@angular/core';
import {AuthService} from '../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: false,
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})

export class NavbarComponent {
  isScrolled = false;
  isDarkMode = false;
  isMenuOpen = false;
  searchActive = false;
  username = 'John Doe';
  unreadNotifications = 3;
  @ViewChildren('navLinkRefs') navLinkRefs!: QueryList<ElementRef>;
  menuItems = [
    { label: 'Home', icon: 'house-door-fill', route: '/home', active: true },
    { label: 'Customers', icon: 'speedometer2', route: '/customers', active: false },
    { label: 'Account', icon: 'folder2-open', route: '/accounts', active: false },
    { label: 'Services', icon: 'gear-fill', route: '/services', active: false },
    { label: 'About', icon: 'info-circle-fill', route: '/about', active: false },
    { label: 'Contact', icon: 'envelope-fill', route: '/contact', active: false }
  ];

  notifications = [
    { id: 1, title: 'New Update Available', time: '5 min ago', read: false },
    { id: 2, title: 'Welcome to our platform', time: '2 hours ago', read: false },
    { id: 3, title: 'Your account was created', time: '1 day ago', read: false }
  ];

  constructor(public authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    // Check if user prefers dark mode
    this.isDarkMode = window.matchMedia('(prefers-color-scheme: dark)').matches;
    this.applyTheme();
  }

  @HostListener('window:scroll')
  onWindowScroll() {
    this.isScrolled = window.scrollY > 20;
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  toggleSearch() {
    this.searchActive = !this.searchActive;
  }

  toggleTheme() {
    this.isDarkMode = !this.isDarkMode;
    this.applyTheme();
  }

  applyTheme() {
    if (this.isDarkMode) {
      document.body.classList.add('dark-theme');
    } else {
      document.body.classList.remove('dark-theme');
    }
  }

  setActive(index: number): void {
    this.navLinkRefs.forEach((ref, i) => {
      const element = ref.nativeElement;
      if (i === index) {
        element.classList.add('active');
      } else {
        element.classList.remove('active');
      }
    });
  }

  markAsRead(id: number) {
    const notification = this.notifications.find(n => n.id === id);
    if (notification && !notification.read) {
      notification.read = true;
      this.unreadNotifications--;
    }
  }

  markAllAsRead() {
    this.notifications.forEach(notification => {
      notification.read = true;
    });
    this.unreadNotifications = 0;
  }

  handleLogout() {
      this.authService.logout();

  }

  protected readonly UIEvent = UIEvent;
}
