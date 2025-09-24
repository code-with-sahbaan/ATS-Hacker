import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterLink, RouterOutlet } from '@angular/router';
import { MenuItem } from 'primeng/api';
import { Menubar } from 'primeng/menubar';
import { ButtonModule } from 'primeng/button';
import { UiService } from '../../../services/ui.service';

@Component({
  selector: 'app-recruiter-menu',
  imports: [Menubar, RouterOutlet, CommonModule, ButtonModule, RouterLink],
  templateUrl: './recruiter-menu.html',
  styleUrl: './recruiter-menu.css'
})
export class RecruiterMenu {

  activeItem: string = '';

  constructor(public router: Router, public uiService: UiService) { }

  items: MenuItem[] | undefined;
  endItems: MenuItem[] | undefined;

  ngOnInit() {
    this.activeItem = window.location.pathname;
    this.items = [
      {
        label: 'Home',
        icon: 'pi pi-home',
        routerLink: '/recruiter/home',
      },
      {
        label: 'Post Job',
        icon: 'pi pi-briefcase',
        routerLink: '/recruiter/post-job'
      },
      {
        label: 'My Profile',
        icon: 'pi pi-user',
        routerLink: '/recruiter/profile'
      }
    ];

    this.endItems = [
      {
        label: 'Profile',
        icon: 'pi pi-user',
        items: [
          {
            label: 'Overview',
            icon: 'pi pi-table'
          },
          {
            label: 'Basic Info',
            icon: 'pi pi-info-circle'
          },
          {
            label: 'Education',
            icon: 'pi pi-book'
          },
          {
            label: 'Experience',
            icon: 'pi pi-briefcase'
          }
        ]
      }
    ];

    // update active item on route change
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        let links = document.getElementsByClassName('p-menubar-item-link');
        let items = document.getElementsByClassName('p-menubar-item');
        for(let i = 0; i < links.length; i++){
          items[i].classList.remove('p-focus');
          links[i].classList.remove('active');
        }
        this.activeItem = event.urlAfterRedirects;
      }
    });
  }

  isActive(item: MenuItem): boolean {
    return item.routerLink === this.activeItem;
  }

}
