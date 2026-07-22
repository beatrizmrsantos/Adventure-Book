
import { Component, computed, OnInit, signal } from '@angular/core';
import { Book } from '../../shared/models/book.model';
import { BookService } from '../../core/services/book.service';
import { BookTagsPipe } from '../../shared/pipes/book-tags.pipe';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [BookTagsPipe],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent implements OnInit{
    books = signal<Book[]>([]);
    search = signal('');

    constructor(private booService: BookService){}

    ngOnInit(): void {
      this.booService.getBooks().subscribe((books)=>(this.books.set(books)));
    }

    filterBooks = computed(() => {
      const value = this.search().toLowerCase().trim();

      if(!value){
        return this.books();
      }

      return this.books().filter(book => 
        book.title.toLowerCase().includes(value) ||
        book.author.toLowerCase().includes(value)
      );
    });

    onSearch(event: Event): void{
      const value = (event.target as HTMLInputElement).value;
      this.search.set(value);
    }

}
