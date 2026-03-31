<template>
    <h2 class="pt-2 pb-3 title">
        <div class="line"></div>
        <span> <slot></slot> </span>
    </h2>
</template>

<script>
export default {

}
</script>

<style scoped>
.line {
    display: inline-block;
    width: 4px;
    height: 24px;
    background: linear-gradient(#20c997, #198754);
    vertical-align: middle;
    margin-right: 10px;
    position: relative;
    top: -2px;
}

.title:hover {
    color: #198754;
    cursor: pointer;
}
</style>