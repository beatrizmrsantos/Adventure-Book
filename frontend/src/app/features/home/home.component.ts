
import { Component, OnInit, signal } from '@angular/core';
import { Book } from '../../shared/models/book.model';
import { BookService } from '../../core/services/book.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent implements OnInit{
    books = signal<Book[]>([]);

    constructor(private booService: BookService){}

    ngOnInit(): void {
      this.booService.getBooks().subscribe((books)=>(this.books.set(books)));
    }

}
