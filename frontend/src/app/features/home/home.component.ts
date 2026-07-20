import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Book } from '../../shared/models/book.model';
import { BookService } from '../../core/services/book.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent implements OnInit{
    books: Book[] = [];

    constructor(private booService: BookService){}

    ngOnInit(): void {
      this.booService.getBooks().subscribe((books)=>(this.books = books));
    }

}
