@extends('layout.main')
@section('title', 'Accueil - Drink\'s')
@section('content')
    <div class="w-100 p-25" style="padding-top:0;">
        <div class="drink-content">
            @if(isset($search_request))
                <h2 style="margin-bottom: 25px; padding: 15px;">Résutlat(s) pour: {{$search_request}}</h2>
            @endif
            <div class="drink-grid">
                @foreach($drinks as $drink)
                <button href="" class="drink-card"
                data-id="{{$drink->id}}" data-title="{{$drink->title}}"
                data-description="{{$drink->description}}"data-image="{{$drink->image}}">
                    <div class="drink-categories">
                        @php $count = 0 @endphp
                        @foreach($categories as $category)

                            @if($category->recette_id == $drink->id)
                                <span class="drink-category">{{$category->title}}</span>
                                @php $count++ @endphp
                                @if($count == 2)
                                    @break
                                @endif
                            @endif
                        @endforeach
                    </div>
                    <img src="{{$drink->image}}" alt="">
                    <span class="drink-name">{{$drink->title}}</span>
                    <div class="see-drink">
                        <svg viewBox="0 0 22 22" stroke-width="2" stroke="currentColor" fill="none" stroke-linecap="round" stroke-linejoin="round">
                            <path stroke="none" d="M0 0h24v24H0z" fill="none"></path>
                            <line x1="0" y1="12" x2="19" y2="12"></line>
                            <line x1="15" y1="16" x2="19" y2="12"></line>
                            <line x1="15" y1="8" x2="19" y2="12"></line>
                        </svg>
                    </div>
                    <div class="card-circle" style="--color: {{$drink->main_color}}"></div>
                </button>
                @endforeach
            </div>
        </div>
    </div>

    <script src="{{asset('js/gestion-drink.js')}}"></script>

@endsection