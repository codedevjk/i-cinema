import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { Movie } from '../shared/models/models';

import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  private apiUrl = `${environment.apiUrl}/movies`;

  // Shared Search State
  private searchTermSource = new BehaviorSubject<string>('');
  currentSearchTerm$ = this.searchTermSource.asObservable();

  constructor(private http: HttpClient) { }

  updateSearchTerm(term: string): void {
    this.searchTermSource.next(term);
  }

  getAllMovies(): Observable<Movie[]> {
    return this.http.get<Movie[]>(this.apiUrl);
  }

  searchMovies(title: string): Observable<Movie[]> {
    const params = new HttpParams().set('title', title);
    return this.http.get<Movie[]>(`${this.apiUrl}/search`, { params });
  }

  getMovieById(id: number): Observable<Movie> {
    return this.http.get<Movie>(`${this.apiUrl}/${id}`);
  }

  rateMovie(id: number, rating: number): Observable<Movie> {
    const params = new HttpParams().set('rating', rating);
    return this.http.post<Movie>(`${this.apiUrl}/${id}/rate`, {}, { params });
  }
}
