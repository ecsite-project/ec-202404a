$(function() {
    const $pagination = $(".cdp");
    const $links = $pagination.find(".cdp_i");
    const toralPages = $links.length;
    const currentPage = parseInt($pagination.attr("actpage"), 10);

    const updatePagination = () => {
        $links.css("display", "inline-flex");

        $links.eq(currentPage - 1).addClass("active");
        $links.eq(currentPage - 1).on("click", (e) => {
            e.preventDefault();
        })
    }

    updatePagination();
})