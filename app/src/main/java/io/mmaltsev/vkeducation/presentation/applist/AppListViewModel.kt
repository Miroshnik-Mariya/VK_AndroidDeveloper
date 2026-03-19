// Новая версия ViewModel с репозиторием
class AppListViewModel(
    private val repository: AppRepository  // добавить зависимость
) : ViewModel() {

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            val apps = repository.getApps()  // данные из репозитория
            _apps.value = apps
        }
    }
    // остальной код без изменений
}